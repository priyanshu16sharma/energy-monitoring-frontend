package com.priyanshu.energy.monitoring.Service.utilityPriceCatalog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.priyanshu.energy.monitoring.dto.utilityPriceCatalogDTO.UtilityPriceCatalogDTO;
import com.priyanshu.energy.monitoring.dto.utilityPriceCatalogDTO.UtilityPriceCatalogUpdateDTO;
import com.priyanshu.energy.monitoring.entity.utilityPriceCatalog.UtilityPriceCatalogEntity;
import com.priyanshu.energy.monitoring.repository.utilityPriceCatalog.UtilityPriceCatalogRepository;

@Service
public class UtilityPriceCatalogService {
    private final UtilityPriceCatalogRepository utilityPriceCatalogRepository;

    @Autowired
    public UtilityPriceCatalogService(UtilityPriceCatalogRepository utilityPriceCatalogRepository) {
        this.utilityPriceCatalogRepository = utilityPriceCatalogRepository;
    }

    public List<UtilityPriceCatalogDTO> getAllCatalogEntries() {
        return utilityPriceCatalogRepository.findAll()
                .stream()
                .map(this::catalogEntityToDTO)
                .toList();
    }

    public UtilityPriceCatalogDTO getIndividualCatalogEntry(String id) throws Exception {
        UtilityPriceCatalogEntity catalogEntry = utilityPriceCatalogRepository.findById(id)
                .orElseThrow(
                        () -> new Exception(new IllegalArgumentException("No catalog entry found for the given id")));

        return catalogEntityToDTO(catalogEntry);
    }

    public UtilityPriceCatalogDTO deleteUtilityPriceCatalogEntry(String id) throws Exception {
        UtilityPriceCatalogEntity catalogEntry = utilityPriceCatalogRepository.findById(id)
                .orElseThrow(
                        () -> new Exception(new IllegalArgumentException("No catalog entry found for the given id")));

        LocalDate currentDate = LocalDate.now();

        if (catalogEntry.getEffectiveFrom().isBefore(currentDate)) {
            throw new IllegalArgumentException("Previously Effective Records Cannot be deleted");
        }

        utilityPriceCatalogRepository.deleteById(id);

        return catalogEntityToDTO(catalogEntry);

    }

    public UtilityPriceCatalogDTO updateUtilityPriceCatalogEntry(String id,
            UtilityPriceCatalogUpdateDTO updatedCatalogData) {

        UtilityPriceCatalogEntity existingCatalogData = utilityPriceCatalogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No user found with the given ID"));

        LocalDate currDate = LocalDate.now();

        if (existingCatalogData.getEffectiveFrom().isBefore(currDate)) {
            throw new IllegalArgumentException("This pricing has already been implemented and cannot be changed");
        }

        if (updatedCatalogData.getCity() != null)
            existingCatalogData.setCity(updatedCatalogData.getCity());
        if (updatedCatalogData.getPincode() != null)
            existingCatalogData.setPincode(updatedCatalogData.getPincode());
        if (updatedCatalogData.getState() != null)
            existingCatalogData.setState(updatedCatalogData.getState());
        if (updatedCatalogData.getFixedChargeHousehold() != null)
            existingCatalogData.setFixedChargeHousehold(updatedCatalogData.getFixedChargeHousehold());
        if (updatedCatalogData.getFixedChargeIndustrial() != null)
            existingCatalogData.setFixedChargeHousehold(updatedCatalogData.getFixedChargeIndustrial());
        if (updatedCatalogData.getFpppa() != null)
            existingCatalogData.setFpppa(updatedCatalogData.getFpppa());
        if (updatedCatalogData.getTaxHousehold() != null)
            existingCatalogData.setTaxHousehold(updatedCatalogData.getTaxHousehold());
        if (updatedCatalogData.getTaxIndustrial() != null)
            existingCatalogData.setTaxIndustrial(updatedCatalogData.getTaxIndustrial());
        if (updatedCatalogData.getRatePerUnitHouseHold() != null)
            existingCatalogData.setRatePerUnitHouseHold(updatedCatalogData.getRatePerUnitHouseHold());
        if (updatedCatalogData.getRatePerUnitIndustrial() != null)
            existingCatalogData.setRatePerUnitIndustrial(updatedCatalogData.getRatePerUnitIndustrial());
        if (updatedCatalogData.getEffectiveFrom() != null)
            existingCatalogData.setEffectiveFrom(updatedCatalogData.getEffectiveFrom());

        UtilityPriceCatalogEntity updatedCatalogEntry = utilityPriceCatalogRepository.save(existingCatalogData);

        return catalogEntityToDTO(updatedCatalogEntry);

    }

    public UtilityPriceCatalogDTO createUtilityPriceCatalogEntry(
            UtilityPriceCatalogDTO utilityPriceCatalogEntryData) {
        System.out.println(utilityPriceCatalogEntryData);

        UtilityPriceCatalogEntity priceCatalogData = new UtilityPriceCatalogEntity();
        priceCatalogData.setPincode(utilityPriceCatalogEntryData.getPincode());
        priceCatalogData.setCity(utilityPriceCatalogEntryData.getCity());
        priceCatalogData.setState(utilityPriceCatalogEntryData.getState());
        priceCatalogData.setRatePerUnitHouseHold(utilityPriceCatalogEntryData.getRatePerUnitHouseHold());
        priceCatalogData.setRatePerUnitIndustrial(utilityPriceCatalogEntryData.getRatePerUnitIndustrial());
        priceCatalogData.setFixedChargeHousehold(utilityPriceCatalogEntryData.getFixedChargeHousehold());
        priceCatalogData.setFixedChargeIndustrial(utilityPriceCatalogEntryData.getFixedChargeIndustrial());
        priceCatalogData.setTaxHousehold(utilityPriceCatalogEntryData.getTaxHousehold());
        priceCatalogData.setTaxIndustrial(utilityPriceCatalogEntryData.getTaxIndustrial());
        priceCatalogData.setFpppa(utilityPriceCatalogEntryData.getFpppa());
        priceCatalogData.setEffectiveFrom(utilityPriceCatalogEntryData.getEffectiveFrom());
        priceCatalogData.setCreatedAt(LocalDateTime.now());

        UtilityPriceCatalogEntity savedCatalogData = utilityPriceCatalogRepository.save(priceCatalogData);
        UtilityPriceCatalogDTO responseDTO = catalogEntityToDTO(savedCatalogData);
        return responseDTO;
    }

    public UtilityPriceCatalogDTO catalogEntityToDTO(UtilityPriceCatalogEntity utilityPriceCatalogEntity) {

        UtilityPriceCatalogDTO UtilityPriceCatalogCreateDTO = new UtilityPriceCatalogDTO();

        UtilityPriceCatalogCreateDTO.setId(utilityPriceCatalogEntity.getId());
        UtilityPriceCatalogCreateDTO.setPincode(utilityPriceCatalogEntity.getPincode());
        UtilityPriceCatalogCreateDTO.setCity(utilityPriceCatalogEntity.getCity());
        UtilityPriceCatalogCreateDTO.setState(utilityPriceCatalogEntity.getState());
        UtilityPriceCatalogCreateDTO.setRatePerUnitHouseHold(utilityPriceCatalogEntity.getRatePerUnitHouseHold());
        UtilityPriceCatalogCreateDTO.setRatePerUnitIndustrial(utilityPriceCatalogEntity.getRatePerUnitIndustrial());
        UtilityPriceCatalogCreateDTO.setFixedChargeHousehold(utilityPriceCatalogEntity.getFixedChargeHousehold());
        UtilityPriceCatalogCreateDTO.setFixedChargeIndustrial(utilityPriceCatalogEntity.getFixedChargeIndustrial());
        UtilityPriceCatalogCreateDTO.setTaxHousehold(utilityPriceCatalogEntity.getTaxHousehold());
        UtilityPriceCatalogCreateDTO.setTaxIndustrial(utilityPriceCatalogEntity.getTaxIndustrial());
        UtilityPriceCatalogCreateDTO.setFpppa(utilityPriceCatalogEntity.getFpppa());
        UtilityPriceCatalogCreateDTO.setEffectiveFrom(utilityPriceCatalogEntity.getEffectiveFrom());
        UtilityPriceCatalogCreateDTO.setCreatedAt(utilityPriceCatalogEntity.getCreatedAt());

        return UtilityPriceCatalogCreateDTO;
    }

}
