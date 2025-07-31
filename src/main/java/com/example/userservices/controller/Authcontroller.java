package com.example.userservices.controller;

import com.example.userservices.dto.RegisterRequest;
import com.example.userservices.dto.LoginRequest;
import com.example.userservices.service.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class Authcontroller {

    @Autowired
    private KeycloakService keycloakService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return keycloakService.registerUser(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return keycloakService.loginUser(request);


    }
}
