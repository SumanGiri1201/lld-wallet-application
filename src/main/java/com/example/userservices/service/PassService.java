package com.example.userservices.service;

import com.example.userservices.model.Pass;
import com.example.userservices.Repository.PassRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassService {

    private final PassRepository passRepository;

    public PassService(PassRepository passRepository) {
        this.passRepository = passRepository;
    }

    public Pass createPass(Pass pass) {
        return passRepository.save(pass);
    }

    public List<Pass> getAllPasses() {
        return passRepository.findAll();
    }

    public Optional<Pass> getPassById(Long id) {
        return passRepository.findById(id);
    }

    public void deletePass(Long id) {
        passRepository.deleteById(id);
    }

    public List<Pass> getPassesByUserEmail(String email) {
        return passRepository.findByUserEmail(email);
    }
}
