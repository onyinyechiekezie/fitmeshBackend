package com.fitmesh.fitmeshbackend.domain.exception;

public class PaymentNotFoundException extends DomainException {
    public PaymentNotFoundException(String reference) {
        super("Payment not found with reference: " + reference);
    }
}
