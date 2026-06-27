package com.fitmesh.fitmeshbackend.adapter.in.web.dto.response;

import java.util.List;

public class AuthenticationResponse {
    private final String userId;
    private final String email;
    private final String token;

    public AuthenticationResponse(String userId, String email, String token) {
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