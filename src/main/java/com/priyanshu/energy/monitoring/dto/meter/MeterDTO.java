package com.priyanshu.energy.monitoring.dto.meter;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeterDTO {
    private String id;

    @NotNull
    @Pattern(regexp = "^(industry|household)$", message = "usageType Must be one of industry or household")
    private String usageType;

    @NotNull
    private boolean isActive;

    private LocalDateTime createdAt;
}
