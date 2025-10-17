package com.priyanshu.energy.monitoring.dto.auth;

import com.priyanshu.energy.monitoring.dto.userdto.AdminDTO;
import com.priyanshu.energy.monitoring.dto.userdto.CustomerDTO;
import com.priyanshu.energy.monitoring.dto.userdto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifiedDTO {
    private CustomerDTO customer;
    private AdminDTO Admin;
    private String token;
}
