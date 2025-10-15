package com.priyanshu.energy.monitoring.dto.meter;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeterCreateDTO {
    private String id;

    @NotNull
    private String usageType;

    @NotNull
    private boolean isActive = true;

    private LocalDateTime createdAt;

}
