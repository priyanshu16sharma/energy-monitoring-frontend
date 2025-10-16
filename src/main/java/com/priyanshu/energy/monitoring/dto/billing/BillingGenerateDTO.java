package com.priyanshu.energy.monitoring.dto.billing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingGenerateDTO {

    @NotBlank
    @NotNull
    private String customerId;

    @NotNull
    private LocalDate calcFrom;

    @NotNull
    private LocalDate calcTo;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = true, message = "Total units must be greater than or equal to 0.00")
    private BigDecimal totalUnits;
}
