package com.fitmesh.fitmeshbackend.application.ports.in.command;

import java.util.Objects;

public class LoginCommand {

    private final String email;
    private final String password;

    public LoginCommand(String email, String password) {
        this.email = Objects.requireNonNull(email, "email must not be null");
        this.password = Objects.requireNonNull(password, "password must not be null");
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
