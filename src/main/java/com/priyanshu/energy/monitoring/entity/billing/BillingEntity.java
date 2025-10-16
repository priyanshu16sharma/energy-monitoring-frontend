package com.priyanshu.energy.monitoring.entity.billing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.GenericGenerator;

import com.priyanshu.energy.monitoring.entity.meter.MeterEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

@Data
@Entity
@Table(name = "billing")
@Check(constraints = "total_units >= 0 AND calc_from < calc_to AND base_amount>=0 AND fppa_charge>=0 AND fixed_charge>=0 AND tax_amount>=0 AND total_amount>=0")
public class BillingEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meter_id", nullable = false)
    private MeterEntity meter;

    @DecimalMin("0.0")
    @Column(name = "total_units", nullable = false)
    private BigDecimal totalUnits;

    @DecimalMin("0.0")
    @Column(name = "base_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal baseAmount;

    @DecimalMin("0.0")
    @Column(name = "fppa_charge", nullable = false, precision = 10, scale = 2)
    private BigDecimal fpppaCharge;

    @DecimalMin("0.0")
    @Column(name = "fixed_charge", nullable = false, precision = 10, scale = 2)
    private BigDecimal fixedCharge;

    @DecimalMin("0.0")
    @Column(name = "tax_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal taxAmount;

    @DecimalMin("0.0")
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "calc_from", nullable = false)
    private LocalDate calcFrom;

    @Column(name = "calc_to", nullable = false)
    private LocalDate calcTo;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
