package com.fitmesh.fitmeshbackend.domain.exception;

public class InactivePlanException extends DomainException {
    public InactivePlanException(String planId) {
        super("Cannot subscribe to inactive plan: " + planId);
    }
}