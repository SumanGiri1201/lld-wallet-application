package com.example.userservices.controller;

import com.example.userservices.dto.RegisterRequest;
import com.example.userservices.service.KeycloakService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class Authcontroller {

    private final KeycloakService keycloakService;

    public Authcontroller(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        System.out.println("Registering user: " + request.getEmail());
        String result = keycloakService.registerUser(request);
        return ResponseEntity.ok(result);
    }
}
