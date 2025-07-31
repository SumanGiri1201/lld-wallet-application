package com.example.userservices.Repository;

import com.example.userservices.model.Pass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassRepository extends JpaRepository<Pass, Long> {
    List<Pass> findByUserEmail(String userEmail);
}
