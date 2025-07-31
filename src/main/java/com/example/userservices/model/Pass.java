package com.example.userservices.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "passes")
public class Pass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "pass_status", nullable = false)
    private String passStatus;

    @Column(name = "pass_type", nullable = false)
    private String passType;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "file_uploaded_at")
    private LocalDateTime fileUploadedAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassStatus() {
        return passStatus;
    }

    public void setPassStatus(String passStatus) {
        this.passStatus = passStatus;
    }

    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public LocalDateTime getFileUploadedAt() {
        return fileUploadedAt;
    }

    public void setFileUploadedAt(LocalDateTime fileUploadedAt) {
        this.fileUploadedAt = fileUploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.fileUploadedAt = uploadedAt;
    }
}
