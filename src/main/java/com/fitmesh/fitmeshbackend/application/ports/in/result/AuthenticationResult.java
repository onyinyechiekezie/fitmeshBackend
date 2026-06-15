package com.fitmesh.fitmeshbackend.application.ports.in.result;

public class AuthenticationResult {

    private final String userId;
    private final String email;
    private final String token;

    public AuthenticationResult(String userId, String email, String token) {
        this.userId = userId;
        this.email = email;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}
