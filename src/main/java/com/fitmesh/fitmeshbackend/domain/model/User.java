package com.fitmesh.fitmeshbackend.domain.model;

import com.fitmesh.fitmeshbackend.domain.enums.UserRole;
import com.fitmesh.fitmeshbackend.domain.enums.UserStatus;
import com.fitmesh.fitmeshbackend.domain.exception.InvalidPaymentStateException;
import com.fitmesh.fitmeshbackend.domain.exception.InvalidPhoneNumberException;
import com.fitmesh.fitmeshbackend.domain.exception.UserCannotInitiatePaymentException;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {

    private final String userId;
    private final String email;
    private final String phoneNumber;
    private final String passwordHash;
    private final String firstname;
    private final String lastname;
    private final Set<UserRole> roles;

    private UserStatus status;
    private final Instant registeredAt;
    private Instant lastLoginAt;
    private Instant updatedAt;

    // Creation constructor
    public User(
            String userId,
            String email,
            String phoneNumber,
            String passwordHash,
            String firstname,
            String lastname,
            Set<UserRole> roles
    ) {
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.email = Objects.requireNonNull(email, "email must not be null");
        this.phoneNumber = validatePhoneNumber(phoneNumber);
        this.passwordHash = Objects.requireNonNull(passwordHash, "passwordHash must not be null");
        this.firstname = firstname;
        this.lastname = lastname;
        this.roles = roles != null ? new HashSet<>(roles) : new HashSet<>();

        if (this.roles.isEmpty()) {
            this.roles.add(UserRole.MEMBER);
        }

        this.status = UserStatus.ACTIVE;
        this.registeredAt = Instant.now();
        this.updatedAt = this.registeredAt;
    }

    // Reconstruction constructor (for repository adapters)
    public User(
            String userId,
            String email,
            String phoneNumber,
            String passwordHash,
            String firstname,
            String lastname,
            Set<UserRole> roles,
            UserStatus status,
            Instant registeredAt,
            Instant lastLoginAt,
            Instant updatedAt
    ) {
        this.userId = Objects.requireNonNull(userId);
        this.email = Objects.requireNonNull(email);
        this.phoneNumber = Objects.requireNonNull(phoneNumber);
        this.passwordHash = Objects.requireNonNull(passwordHash);
        this.firstname = firstname;
        this.lastname = lastname;
        this.roles = roles != null ? new HashSet<>(roles) : new HashSet<>();
        this.status = Objects.requireNonNull(status);
        this.registeredAt = Objects.requireNonNull(registeredAt);
        this.lastLoginAt = lastLoginAt;
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    // VALIDATION

    private String validatePhoneNumber(String phoneNumber) {
        Objects.requireNonNull(phoneNumber, "phoneNumber must not be null");

        String normalized = phoneNumber.trim();

        // Must be exactly 13 digits starting with 234 (Nigerian format)
        if (!normalized.matches("^234\\d{10}$")) {
            throw new InvalidPhoneNumberException(phoneNumber);
        }

        return normalized;
    }

    // BUSINESS LOGIC

    public boolean canInitiatePayment() {
        return this.status == UserStatus.ACTIVE;
    }

    public void validateCanInitiatePayment() {
        if (this.status == UserStatus.SUSPENDED) {
            throw new UserCannotInitiatePaymentException(
                    "User account is suspended"
            );
        }

        if (this.status == UserStatus.DELETED) {
            throw new UserCannotInitiatePaymentException(
                    "User account is deleted"
            );
        }

        if (this.status != UserStatus.ACTIVE) {
            throw new UserCannotInitiatePaymentException(
                    "User account is not active"
            );
        }
    }

    public boolean hasRole(UserRole role) {
        return this.roles.contains(role);
    }

    public boolean isAdmin() {
        return this.roles.contains(UserRole.ADMIN);
    }

    public void suspend() {
        if (this.status == UserStatus.DELETED) {
            throw new InvalidPaymentStateException(
                    "Cannot suspend a deleted user"
            );
        }

        this.status = UserStatus.SUSPENDED;
        touch();
    }

    public void reactivate() {
        if (this.status == UserStatus.DELETED) {
            throw new InvalidPaymentStateException(
                    "Cannot reactivate a deleted user"
            );
        }

        this.status = UserStatus.ACTIVE;
        touch();
    }

    public void recordLogin() {
        this.lastLoginAt = Instant.now();
        touch();
    }

    private void touch() {
        this.updatedAt = Instant.now();
    }

    // GETTERS

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Set<UserRole> getRoles() {
        return Collections.unmodifiableSet(roles);
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

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    // VALUE OBJECT CONTRACT

    @Override
    public boolean equals(Object comparisonObject) {
        if (this == comparisonObject) return true;
        if (!(comparisonObject instanceof User)) return false;
        User other = (User) comparisonObject;
        return userId.equals(other.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}