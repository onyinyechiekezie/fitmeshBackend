package com.fitmesh.fitmeshbackend.domain.exception;

public class InvalidPaymentReferenceException extends DomainException{

    public InvalidPaymentReferenceException(String reason) {
        super("Invalid payment reference: " + reason);
    }
}
