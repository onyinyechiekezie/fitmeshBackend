package com.fitmesh.fitmeshbackend.domain.exception;

public class UserNotFoundException extends DomainException {
    public UserNotFoundException(String userId) {
        super("User not found: " + userId);
    }
}
