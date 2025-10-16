package com.priyanshu.energy.monitoring.Service.user;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;

import com.priyanshu.energy.monitoring.Service.jwtService.JWTService;
import com.priyanshu.energy.monitoring.dto.auto.LoginRequestDTO;
import com.priyanshu.energy.monitoring.dto.userdto.UserDTO;
import com.priyanshu.energy.monitoring.entity.user.UserEntity;
import com.priyanshu.energy.monitoring.repository.user.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager,
            JWTService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String verifyUsers(LoginRequestDTO data) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword()));

        if (auth.isAuthenticated()) {
            return jwtService.generateToken(auth.getName());
        }

        return "fail";
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
