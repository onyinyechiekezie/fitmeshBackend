package com.fitmesh.fitmeshbackend.application.ports.in.command;

import java.util.Objects;

public class RegisterUserCommand {

    private final String email;
    private final String phoneNumber;
    private final String password;
    private final String firstname;
    private final String lastname;

    public RegisterUserCommand(
            String email,
            String phoneNumber,
            String password,
            String firstname,
            String lastname
    ) {
        this.email = Objects.requireNonNull(email, "email must not be null");
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber must not be null");
        this.password = Objects.requireNonNull(password, "password must not be null");
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}