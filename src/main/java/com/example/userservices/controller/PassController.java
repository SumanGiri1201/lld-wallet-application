package com.example.userservices.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/passes")
public class PassController {

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPass(@RequestParam("file") MultipartFile file,
                                             @AuthenticationPrincipal Jwt jwt) {
        try {
            String username = jwt != null ? jwt.getClaimAsString("preferred_username") : "unknown";

            String uploadDir = System.getProperty("user.dir") + File.separator + "passes";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String filename = username + "_" + file.getOriginalFilename();
            File destination = new File(dir, filename);
            file.transferTo(destination);

            return ResponseEntity.ok("Pass uploaded successfully: " + filename);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error uploading pass: " + e.getMessage());
        }
    }

    @GetMapping("/view")
    public ResponseEntity<List<String>> viewPasses(@AuthenticationPrincipal Jwt jwt) {
        try {
            String username = jwt != null ? jwt.getClaimAsString("preferred_username") : "unknown";
            String uploadDir = System.getProperty("user.dir") + File.separator + "passes";

            File dir = new File(uploadDir);
            List<String> result = new ArrayList<>();

            if (dir.exists()) {
                File[] files = dir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.getName().startsWith(username + "_")) {
                            result.add(file.getName());
                        }
                    }
                }
            }

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<String> deletePass(@PathVariable String filename,
                                             @AuthenticationPrincipal Jwt jwt) {
        try {
            String username = jwt != null ? jwt.getClaimAsString("preferred_username") : "unknown";
            String expectedPrefix = username + "_";

            if (!filename.startsWith(expectedPrefix)) {
                return ResponseEntity.status(403).body("You are not authorized to delete this file.");
            }

            String uploadDir = System.getProperty("user.dir") + File.separator + "passes";
            File file = new File(uploadDir, filename);

            if (file.exists()) {
                Files.delete(file.toPath());
                return ResponseEntity.ok("Deleted pass: " + filename);
            } else {
                return ResponseEntity.status(404).body("File not found: " + filename);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error deleting pass: " + e.getMessage());
        }
    }
}
