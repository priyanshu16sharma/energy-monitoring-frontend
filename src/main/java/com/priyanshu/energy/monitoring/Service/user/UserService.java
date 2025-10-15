package com.priyanshu.energy.monitoring.Service.user;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.priyanshu.energy.monitoring.dto.auto.LoginRequestDTO;
import com.priyanshu.energy.monitoring.dto.userdto.AdminDTO;
import com.priyanshu.energy.monitoring.dto.userdto.UserDTO;
import com.priyanshu.energy.monitoring.entity.user.AdminEntity;
import com.priyanshu.energy.monitoring.entity.user.UserEntity;
import com.priyanshu.energy.monitoring.repository.user.AdminRepository;
import com.priyanshu.energy.monitoring.repository.user.CustomerRepository;
import com.priyanshu.energy.monitoring.repository.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;

    }

    public String verifyUsers(LoginRequestDTO data) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword()));

        if (auth.isAuthenticated()) {
            return "Success";
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
