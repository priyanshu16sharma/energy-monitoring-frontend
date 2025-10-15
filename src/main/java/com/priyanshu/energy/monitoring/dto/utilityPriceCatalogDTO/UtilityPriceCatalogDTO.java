package com.priyanshu.energy.monitoring.dto.utilityPriceCatalogDTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilityPriceCatalogDTO {

    private String id;

    @NotBlank(message = "Pincode is required")
    @Size(min = 6, max = 6, message = "Pincode must be 6 characters long")
    private String pincode;

    @NotBlank(message = "City is required")
    @Size(max = 56, message = "City cannot exceed 56 characters")
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 56, message = "State cannot exceed 56 characters")
    private String state;

    @NotNull(message = "Rate per unit household is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Rate must be positive")
    @Digits(integer = 8, fraction = 2, message = "Invalid format for rate per unit household")
    private BigDecimal ratePerUnitHouseHold;

    @NotNull(message = "Rate per unit industrial is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Rate must be positive")
    @Digits(integer = 8, fraction = 2, message = "Invalid format for rate per unit industrial")
    private BigDecimal ratePerUnitIndustrial;

    @NotNull(message = "Fixed charge household is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Fixed charge must be positive")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal fixedChargeHousehold;

    @NotNull(message = "Fixed charge industrial is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Fixed charge must be positive")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal fixedChargeIndustrial;

    @NotNull(message = "Tax household is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Tax must be non-negative")
    @DecimalMax(value = "100.0", inclusive = true, message = "Tax cannot exceed 100%")
    @Digits(integer = 3, fraction = 2)
    private BigDecimal taxHousehold;

    @NotNull(message = "Tax industrial is required")
    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "100.0", inclusive = true)
    @Digits(integer = 3, fraction = 2)
    private BigDecimal taxIndustrial;

    @NotNull(message = "FPPPA is required")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 8, fraction = 2)
    private BigDecimal fpppa;

    @NotNull(message = "Effective from date is required")
    private LocalDate effectiveFrom;

    private LocalDateTime createdAt;

}
