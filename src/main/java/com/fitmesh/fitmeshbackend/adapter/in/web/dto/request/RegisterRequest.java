package com.fitmesh.fitmeshbackend.adapter.in.web.dto.request;

public class RegisterRequest {

    private String email;
    private String phoneNumber;
    private String password;
    private String firstname;
    private String lastname;

    public RegisterRequest() {}

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}