package com.example.userservices.service;

import com.example.userservices.dto.UpdateProfile;
import com.example.userservices.model.Profile;
import com.example.userservices.model.User;
import com.example.userservices.Repository.ProfileRepository;
import com.example.userservices.Repository.UserRepository;
import jakarta.transaction.Transactional;
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

    public String createProfile(UpdateProfile updateProfile, Principal principal) {
        String email = principal.getName(); // email from token
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        User user = optionalUser.get();

        Profile profile = user.getProfile();
        if (profile == null) {
            profile = new Profile();
            profile.setUser(user);
        }

        profile.setFullName(updateProfile.getFullName());
        profile.setPhoneNumber(updateProfile.getPhoneNumber());
        profile.setPincode(updateProfile.getPincode());

        profileRepository.save(profile);

        if (user.getProfile() == null) {
            user.setProfile(profile);
            userRepository.save(user);
        }

        return "Profile created/updated successfully";
    }

    public Profile getProfile(Principal principal) {
        String email = principal.getName();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        return optionalUser.map(User::getProfile).orElse(null);
    }

    @Transactional
    public String updateProfile(UpdateProfile updateProfile, Principal principal) {
        return createProfile(updateProfile, principal);
    }

    public String deleteProfile(Principal principal) {
        String email = principal.getName();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        User user = optionalUser.get();
        Profile profile = user.getProfile();

        if (profile == null) {
            return "Profile not found";
        }

        profileRepository.delete(profile);
        user.setProfile(null);
        userRepository.save(user);

        return "Profile deleted successfully";
    }
}
