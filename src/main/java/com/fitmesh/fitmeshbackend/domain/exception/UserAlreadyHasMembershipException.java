package com.fitmesh.fitmeshbackend.domain.exception;

public class UserAlreadyHasMembershipException extends DomainException {
    public UserAlreadyHasMembershipException(String userId) {
        super("User already has an active membership: " + userId);
    }
}
