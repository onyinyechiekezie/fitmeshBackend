package com.fitmesh.fitmeshbackend.domain.exception;

public class ProviderReferenceAlreadyAttachedException extends DomainException {
    public ProviderReferenceAlreadyAttachedException(String paymentId) {

        super("Provider reference already attached for payment: " + paymentId);
    }
}
