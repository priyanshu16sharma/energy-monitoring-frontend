package com.priyanshu.energy.monitoring.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.priyanshu.energy.monitoring.Service.user.UserService;
import com.priyanshu.energy.monitoring.dto.auto.LoginRequestDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("")
public class AuthLogin {

    public final UserService useService;

    @Autowired
    public AuthLogin(UserService userService) {
        this.useService = userService;
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequestDTO data) {
        return useService.verifyUsers(data);
    }

    @GetMapping("/login")
    public String login() {
        return "Successful";
    }
}
