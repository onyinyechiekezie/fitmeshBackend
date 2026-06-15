package com.fitmesh.fitmeshbackend.domain.exception;

public class InvalidWebhookDataException extends DomainException {
    public InvalidWebhookDataException(String field) {
        super("Invalid or missing webhook data: " + field);
    }
}
