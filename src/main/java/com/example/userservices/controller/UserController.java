package com.example.userservices.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/profile")
    public String getProfile() {
        return "This is a protected user profile. You are authenticated!";
    }
}

