package com.priyanshu.energy.monitoring.dto.userdto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
    private String id;

    @NotNull(message = "Assigned pincode cannot be null")
    private String assignedPincode;

    @NotNull(message = "User information is required")
    @Valid
    private UserDTO user;
}
