package com.example.userservices.service;

import com.example.userservices.model.User;
import com.example.userservices.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public Optional<User> getProfile(Principal principal) {
        String email = principal.getName(); // Keycloak gives email
        return userRepository.findByEmail(email);
    }


    public Optional<User> updateProfile(Principal principal, User updatedUser) {
        String email = principal.getName();
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User user = userOptional.get();
        user.setFullName(updatedUser.getFullName());
        user.setPhone(updatedUser.getPhone());
        user.setAddress(updatedUser.getAddress());

        userRepository.save(user);
        return Optional.of(user);
    }


    public boolean deleteProfile(Principal principal) {
        String email = principal.getName();
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return true;
        }

        return false;
    }
}
