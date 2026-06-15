package com.fitmesh.fitmeshbackend.application.ports.in.command;

import java.util.Objects;

public class ResumeMembershipCommand {

    private final String userId;

    public ResumeMembershipCommand(String userId) {
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
    }

    public String getUserId() {
        return userId;
    }
}
