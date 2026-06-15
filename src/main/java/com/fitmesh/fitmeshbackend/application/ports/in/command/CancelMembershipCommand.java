package com.fitmesh.fitmeshbackend.application.ports.in.command;

import java.util.Objects;

public class CancelMembershipCommand {

    private final String userId;

    public CancelMembershipCommand(String userId) {
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
    }

    public String getUserId() {
        return userId;
    }
}
