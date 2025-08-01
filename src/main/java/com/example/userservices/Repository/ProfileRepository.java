package com.example.userservices.Repository;

import com.example.userservices.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByKeycloakId(String keycloakId);
    void deleteByKeycloakId(String keycloakId);
}
