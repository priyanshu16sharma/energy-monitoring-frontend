package com.priyanshu.energy.monitoring.Service.user;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;

import com.priyanshu.energy.monitoring.Service.jwtService.JWTService;
import com.priyanshu.energy.monitoring.dto.auth.VerifiedDTO;
import com.priyanshu.energy.monitoring.dto.auto.LoginRequestDTO;
import com.priyanshu.energy.monitoring.dto.userdto.AdminDTO;
import com.priyanshu.energy.monitoring.dto.userdto.CustomerDTO;
import com.priyanshu.energy.monitoring.dto.userdto.UserDTO;
import com.priyanshu.energy.monitoring.entity.user.CustomerEntity;
import com.priyanshu.energy.monitoring.entity.user.UserEntity;
import com.priyanshu.energy.monitoring.repository.user.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final CustomerService customerService;
    private final AdminService adminService;

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager,
            JWTService jwtService, CustomerService customerService, AdminService adminService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customerService = customerService;
        this.adminService = adminService;
    }

    public VerifiedDTO verifyUsers(LoginRequestDTO data) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword()));

        if (auth.isAuthenticated()) {
            UserEntity user = userRepository.findByEmail(data.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Email or Password"));
            String token = jwtService.generateToken(auth.getName());

            CustomerDTO customer = user.getRole().toLowerCase() == "customer"
                    ? customerService.mapToDTO(user.getCustomer())
                    : null;

            AdminDTO admin = user.getRole().toLowerCase() == "customer"
                    ? null
                    : adminService.mapToDTO(user.getAdmin());

            return new VerifiedDTO(
                    customer,
                    admin,
                    token);
        }

        throw new IllegalArgumentException("Invalid Email or Password");
    }

    public UserDTO deleteUserById(String id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No User Found with the given ID"));

        userRepository.deleteById(id);
        UserDTO deletedUserDTO = new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                null,
                user.getRole(),
                user.getCreatedAt());
        return deletedUserDTO;
    }

}
