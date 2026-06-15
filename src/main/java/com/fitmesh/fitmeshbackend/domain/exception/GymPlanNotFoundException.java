package com.fitmesh.fitmeshbackend.domain.exception;

public class GymPlanNotFoundException extends DomainException {
    public GymPlanNotFoundException(String planId) {
        super("Gym plan not found: " + planId);
    }
}
