package com.fitmesh.fitmeshbackend.adapter.in.web.dto.response;

import com.fitmesh.fitmeshbackend.domain.enums.UserRole;
import com.fitmesh.fitmeshbackend.domain.enums.UserStatus;

import java.time.Instant;
import java.util.Set;

public class UserProfileResponse {

    private final String userId;
    private final String email;
    private final String phoneNumber;
    private final String firstname;
    private final String lastname;
    private final Set<UserRole> roles;
    private final UserStatus status;
    private final Instant registeredAt;
    private final Instant lastLoginAt;

    public UserProfileResponse(
            String userId,
            String email,
            String phoneNumber,
            String firstname,
            String lastname,
            Set<UserRole> roles,
            UserStatus status,
            Instant registeredAt,
            Instant lastLoginAt
    ) {
        this.userId = userId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.roles = roles;
        this.status = status;
        this.registeredAt = registeredAt;
        this.lastLoginAt = lastLoginAt;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public UserStatus getStatus() {
        return status;
    }

    public Instant getRegisteredAt() {
        return registeredAt;
    }

    public Instant getLastLoginAt() {
        return lastLoginAt;
    }
}