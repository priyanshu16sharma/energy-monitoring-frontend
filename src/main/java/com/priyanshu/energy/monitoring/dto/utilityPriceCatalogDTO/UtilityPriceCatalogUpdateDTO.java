package com.priyanshu.energy.monitoring.dto.utilityPriceCatalogDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilityPriceCatalogUpdateDTO {

    @NotBlank(message = "Id is required for making updates")
    private String id;

    @Size(min = 6, max = 6, message = "Pincode must be 6 characters long")
    private String pincode;

    @Size(max = 56, message = "City cannot exceed 56 characters")
    private String city;

    @Size(max = 56, message = "State cannot exceed 56 characters")
    private String state;

    @DecimalMin(value = "0.0", inclusive = false, message = "Rate must be positive")
    @Digits(integer = 8, fraction = 2, message = "Invalid format for rate per unit household")
    private BigDecimal ratePerUnitHouseHold;

    @DecimalMin(value = "0.0", inclusive = false, message = "Rate must be positive")
    @Digits(integer = 8, fraction = 2, message = "Invalid format for rate per unit industrial")
    private BigDecimal ratePerUnitIndustrial;

    @DecimalMin(value = "0.0", inclusive = false, message = "Fixed charge must be positive")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal fixedChargeHousehold;

    @DecimalMin(value = "0.0", inclusive = false, message = "Fixed charge must be positive")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal fixedChargeIndustrial;

    @DecimalMin(value = "0.0", inclusive = true, message = "Tax must be non-negative")
    @DecimalMax(value = "100.0", inclusive = true, message = "Tax cannot exceed 100%")
    @Digits(integer = 3, fraction = 2)
    private BigDecimal taxHousehold;

    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "100.0", inclusive = true)
    @Digits(integer = 3, fraction = 2)
    private BigDecimal taxIndustrial;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 8, fraction = 2)
    private BigDecimal fpppa;

    private LocalDate effectiveFrom;

    private LocalDateTime createdAt;
}
