package com.priyanshu.energy.monitoring.entity.utilityPriceCatalog;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "utility_price_catalog")
public class UtilityPriceCatalogEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 36)
    private String id;

    @Column(nullable = false, length = 6)
    private String pincode;

    @Column(nullable = false, length = 56)
    private String city;

    @Column(nullable = false, length = 56)
    private String state;

    @Column(name = "rate_per_unit_household", nullable = false, precision = 10, scale = 2)
    private BigDecimal ratePerUnitHouseHold;

    @Column(name = "rate_per_unit_industrial", nullable = false, precision = 10, scale = 2)
    private BigDecimal ratePerUnitIndustrial;

    @Column(name = "fixed_charge_household", nullable = false, precision = 10, scale = 2)
    private BigDecimal fixedChargeHousehold;

    @Column(name = "fixed_charge_industrial", nullable = false, precision = 10, scale = 2)
    private BigDecimal fixedChargeIndustrial;

    @Column(name = "tax_household", nullable = false, precision = 5, scale = 2)
    private BigDecimal taxHousehold;

    @Column(name = "tax_industrial", nullable = false, precision = 5, scale = 2)
    private BigDecimal taxIndustrial;

    @Column(name = "fpppa", nullable = false, precision = 10, scale = 2)
    private BigDecimal fpppa;

    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

}
