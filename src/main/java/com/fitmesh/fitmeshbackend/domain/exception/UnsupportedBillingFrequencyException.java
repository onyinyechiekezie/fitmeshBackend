package com.fitmesh.fitmeshbackend.domain.exception;

public class UnsupportedBillingFrequencyException extends DomainException {
    public UnsupportedBillingFrequencyException(String billingFrequency) {
        super("Unsupported billing frequency: " + billingFrequency);
    }
}
