package com.priyanshu.energy.monitoring.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.priyanshu.energy.monitoring.Service.user.UserService;
import com.priyanshu.energy.monitoring.dto.auth.VerifiedDTO;
import com.priyanshu.energy.monitoring.dto.auto.LoginRequestDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthLogin {

    public final UserService useService;

    @Autowired
    public AuthLogin(UserService userService) {
        this.useService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO data) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(useService.verifyUsers(data));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/login")
    public String login() {
        return "Successful";
    }
}
