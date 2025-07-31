package com.example.userservices.dto;

public class PassRequest {
    private String passType;
    private String passStatus;
    private String fileUrl;
    private Long userId;

    public PassRequest() {}

    public PassRequest(String passType, String passStatus, String fileUrl, Long userId) {
        this.passType = passType;
        this.passStatus = passStatus;
        this.fileUrl = fileUrl;
        this.userId = userId;
    }

    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public String getPassStatus() {
        return passStatus;
    }

    public void setPassStatus(String passStatus) {
        this.passStatus = passStatus;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
