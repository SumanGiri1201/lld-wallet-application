package com.example.userservices.service;

import com.example.userservices.model.Profile;
import com.example.userservices.Repository.ProfileRepository;
import com.example.userservices.Repository.UserRepository;
import com.example.userservices.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    public Profile createProfile(Profile profile, Principal principal) {
        String email = principal.getName();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();
        profile.setUser(user);
        return profileRepository.save(profile);
    }
}
