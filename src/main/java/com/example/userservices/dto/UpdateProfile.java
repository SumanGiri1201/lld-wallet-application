package com.example.userservices.dto;

public class UpdateProfile {
    private String name;
    private String gender;
    private String address;
    private String dob; // Accept string and parse in backend

    public UpdateProfile() {}

    public UpdateProfile(String name, String gender, String address, String dob) {
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}

