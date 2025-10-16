package com.priyanshu.energy.monitoring.dto.billing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.priyanshu.energy.monitoring.dto.meter.MeterDTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingDTO {

    private String id;

    @NotNull(message = "Meter data is required")
    private MeterDTO meter;

    @NotNull(message = "Total units cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Total units must be greater than or equal to 0")
    private BigDecimal totalUnits;

    @NotNull(message = "Base amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Base amount must be greater than or equal to 0")
    private BigDecimal baseAmount;

    @NotNull(message = "FPPPA charge cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "FPPA charge must be greater than or equal to 0")
    private BigDecimal fpppaCharge;

    @NotNull(message = "Fixed charge cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Fixed charge must be greater than or equal to 0")
    private BigDecimal fixedCharge;

    @NotNull(message = "Tax amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Tax amount must be greater than or equal to 0")
    private BigDecimal taxAmount;

    @NotNull(message = "Total amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Total amount must be greater than or equal to 0")
    private BigDecimal totalAmount;

    @NotBlank(message = "Status cannot be blank")
    private String status;

    @NotNull(message = "Calculation start time (calcFrom) cannot be null")
    private LocalDate calcFrom;

    @NotNull(message = "Calculation end time (calcTo) cannot be null")
    private LocalDate calcTo;

    @NotNull(message = "Created at timestamp cannot be null")
    private LocalDateTime createdAt;
}
