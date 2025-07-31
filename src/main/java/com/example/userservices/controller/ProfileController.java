package com.example.userservices.controller;

import com.example.userservices.model.Profile;
import com.example.userservices.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile, Principal principal) {
        Profile savedProfile = profileService.createProfile(profile, principal);
        return ResponseEntity.ok(savedProfile);
    }
}
