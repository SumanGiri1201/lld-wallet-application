package com.example.userservices.controller;

import com.example.userservices.dto.UpdateProfile;
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
    public ResponseEntity<String> createOrUpdateProfile(@RequestBody UpdateProfile request, Principal principal) {
        String response = profileService.createProfile(request, principal);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/me")
    public ResponseEntity<UpdateProfile> getProfile(Principal principal) {
        Profile profile = profileService.getProfile(principal);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }

        UpdateProfile dto = new UpdateProfile();
        dto.setFullName(profile.getFullName());
        dto.setPhoneNumber(profile.getPhoneNumber());
        dto.setPincode(profile.getPincode());

        return ResponseEntity.ok(dto);
    }


    @DeleteMapping("/me")
    public ResponseEntity<String> deleteProfile(Principal principal) {
        profileService.deleteProfile(principal);
        return ResponseEntity.ok("Profile deleted successfully");
    }
}
