package com.fitmesh.fitmeshbackend.domain.exception;

public class UserCannotInitiatePaymentException extends DomainException {
    public UserCannotInitiatePaymentException(String reason) {
        super("User cannot initiate payment: " + reason);
    }
}
