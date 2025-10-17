package com.priyanshu.energy.monitoring.Service.billing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.priyanshu.energy.monitoring.Service.user.CustomerService;
import com.priyanshu.energy.monitoring.dto.billing.BillingDTO;
import com.priyanshu.energy.monitoring.dto.billing.BillingReportDTO;
import com.priyanshu.energy.monitoring.dto.meter.MeterCreateDTO;
import com.priyanshu.energy.monitoring.dto.meter.MeterDTO;
import com.priyanshu.energy.monitoring.entity.billing.BillingEntity;
import com.priyanshu.energy.monitoring.entity.meter.MeterEntity;
import com.priyanshu.energy.monitoring.entity.user.CustomerEntity;
import com.priyanshu.energy.monitoring.entity.utilityPriceCatalog.UtilityPriceCatalogEntity;
import com.priyanshu.energy.monitoring.repository.billing.BillingRepository;
import com.priyanshu.energy.monitoring.repository.meter.MeterRepository;
import com.priyanshu.energy.monitoring.repository.user.CustomerRepository;
import com.priyanshu.energy.monitoring.repository.utilityPriceCatalog.UtilityPriceCatalogRepository;

import jakarta.transaction.Transactional;

@Service
public class BillingService {
    private final BillingRepository billingRepository;
    private final UtilityPriceCatalogRepository catalogRepository;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    @Autowired
    public BillingService(BillingRepository billingRepository,
            UtilityPriceCatalogRepository catalogRepository, CustomerRepository customerRepository,
            CustomerService customerService) {

        this.billingRepository = billingRepository;
        this.catalogRepository = catalogRepository;
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    public List<BillingDTO> getBills() {
        return billingRepository.findAllByOrderByCreatedAtDesc().stream().map(this::toDTO).toList();
    }

    public BillingReportDTO getBillsById(String id) {
        BillingEntity bill = billingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such bill exist"));
        BigDecimal pendingBillAmount = billingRepository.findTotalAmountByMeterIdAndStatus(bill.getMeter().getId(),
                "Pending");
        BillingDTO finalBill = toDTO(bill);

        return new BillingReportDTO(
                finalBill,
                pendingBillAmount,
                customerService.mapToDTO(bill.getMeter().getCustomer()));
    }

    public List<BillingDTO> getBillsByCustomerId(String customerId) {
        CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("No such user exist"));

        return billingRepository.findAllByMeterIdOrderByCreatedAtDesc(customer.getMeter().getId()).stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public BillingDTO generateBill(String customerId, LocalDate calcFrom, LocalDate calcTo,
            BigDecimal totalUnits) {

        LocalDate now = LocalDate.now();

        if (calcTo.isAfter(now)) {
            throw new IllegalArgumentException("Can't create Bill for future date");
        }

        CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Meter not found for customer"));
        MeterEntity meter = customer.getMeter();

        BillingEntity existingLatestBill = billingRepository.findTopByMeterIdOrderByCreatedAtDesc(meter.getId());

        String pincode = meter.getCustomer().getPincode();
        String usageType = meter.getUsageType();

        List<UtilityPriceCatalogEntity> catalogs = catalogRepository
                .findByPincodeAndEffectiveFromBeforeOrderByEffectiveFromAsc(
                        pincode, calcTo);

        if (catalogs.isEmpty()) {
            throw new RuntimeException("No catalog found for pincode " + pincode);
        }

        BigDecimal sumBase = BigDecimal.ZERO;
        BigDecimal sumFixed = BigDecimal.ZERO;
        BigDecimal sumFppa = BigDecimal.ZERO;
        BigDecimal sumTax = BigDecimal.ZERO;

        long totalDays = ChronoUnit.DAYS.between(calcFrom, calcTo) + 1;

        for (int i = 0; i < catalogs.size(); i++) {
            UtilityPriceCatalogEntity catalog = catalogs.get(i);

            LocalDate entryStart = calcFrom.isAfter(catalog.getEffectiveFrom())
                    ? calcFrom
                    : catalog.getEffectiveFrom();

            LocalDate entryEnd;
            if (i + 1 < catalogs.size()) {
                entryEnd = catalogs.get(i + 1).getEffectiveFrom().minusDays(1);
            } else {
                entryEnd = calcTo;
            }

            if (entryEnd.isAfter(calcTo))
                entryEnd = calcTo;
            if (entryEnd.isBefore(entryStart))
                continue;

            long daysInEntry = ChronoUnit.DAYS.between(entryStart, entryEnd) + 1;
            BigDecimal fraction = BigDecimal.valueOf(daysInEntry)
                    .divide(BigDecimal.valueOf(totalDays), 6, RoundingMode.HALF_UP);

            BigDecimal ratePerUnit = usageType.equalsIgnoreCase("HOUSEHOLD")
                    ? catalog.getRatePerUnitHouseHold()
                    : catalog.getRatePerUnitIndustrial();
            BigDecimal fixedCharge = usageType.equalsIgnoreCase("HOUSEHOLD")
                    ? catalog.getFixedChargeHousehold()
                    : catalog.getFixedChargeIndustrial();
            BigDecimal taxPercent = usageType.equalsIgnoreCase("HOUSEHOLD")
                    ? catalog.getTaxHousehold()
                    : catalog.getTaxIndustrial();
            BigDecimal fppa = catalog.getFpppa();

            BigDecimal units = totalUnits.multiply(fraction);

            BigDecimal baseAmount = units.multiply(ratePerUnit);
            BigDecimal fixedProrated = fixedCharge.multiply(fraction);
            BigDecimal fppaProrated = units.multiply(fppa);
            BigDecimal taxAmount = baseAmount.multiply(taxPercent.divide(BigDecimal.valueOf(100)));

            sumBase = sumBase.add(baseAmount);
            sumFixed = sumFixed.add(fixedProrated);
            sumFppa = sumFppa.add(fppaProrated);
            sumTax = sumTax.add(taxAmount);
        }

        BigDecimal totalAmount = sumBase.add(sumFixed).add(sumFppa).add(sumTax);

        BillingEntity billing = new BillingEntity();
        billing.setMeter(meter);
        billing.setCalcFrom(calcFrom);
        billing.setCalcTo(calcTo);
        billing.setTotalUnits(totalUnits);
        billing.setBaseAmount(sumBase.setScale(2, RoundingMode.HALF_UP));
        billing.setFixedCharge(sumFixed.setScale(2, RoundingMode.HALF_UP));
        billing.setFpppaCharge(sumFppa.setScale(2, RoundingMode.HALF_UP));
        billing.setTaxAmount(sumTax.setScale(2, RoundingMode.HALF_UP));
        billing.setTotalAmount(totalAmount.setScale(2, RoundingMode.HALF_UP));
        billing.setStatus("Pending");
        billing.setCreatedAt(LocalDateTime.now());

        BillingEntity savedBill = billingRepository.save(billing);

        return toDTO(savedBill);
    }

    private BillingDTO toDTO(BillingEntity entity) {
        if (entity == null)
            return null;

        BillingDTO dto = new BillingDTO();
        dto.setId(entity.getId());
        dto.setMeter(new MeterDTO(
                entity.getMeter().getId(),
                entity.getMeter().getUsageType(),
                entity.getMeter().isActive(),
                entity.getMeter().getCreatedAt()));
        dto.setTotalUnits(entity.getTotalUnits());
        dto.setBaseAmount(entity.getBaseAmount());
        dto.setFpppaCharge(entity.getFpppaCharge());
        dto.setFixedCharge(entity.getFixedCharge());
        dto.setTaxAmount(entity.getTaxAmount());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setStatus(entity.getStatus());
        dto.setCalcFrom(entity.getCalcFrom());
        dto.setCalcTo(entity.getCalcTo());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }
}
