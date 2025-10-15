package com.priyanshu.energy.monitoring.dto.userdto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String id;

    @NotNull
    @Size(max = 14, message = "Phone number cannot be longer than 14 digits")
    private String phoneNumber;

    @NotNull
    @Size(min = 6, max = 6, message = "Pincode must have 6 characters")
    private String pincode;

    @NotNull
    @Size(max = 255, message = "Address should not exceed 255 characters")
    private String address;

    @NotNull
    @Size(max = 56, message = "City cannot exceed 56 characters")
    private String city;

    @NotNull
    @Size(max = 56, message = "State cannot exceed 56 characters")
    private String state;

    @Valid
    private UserDTO user;

}
