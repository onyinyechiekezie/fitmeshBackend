package com.fitmesh.fitmeshbackend.domain.exception;

public class InvalidPhoneNumberException extends DomainException {
    public InvalidPhoneNumberException(String phoneNumber) {
        super("Invalid phone number format. Expected Nigerian format (234XXXXXXXXXX), got: " + phoneNumber);
    }
}
