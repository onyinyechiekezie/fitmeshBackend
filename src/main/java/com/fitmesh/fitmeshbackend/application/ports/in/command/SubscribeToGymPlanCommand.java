package com.fitmesh.fitmeshbackend.application.ports.in.command;

import java.util.Objects;

public class SubscribeToGymPlanCommand {

    private final String userId;
    private final String planId;

    public SubscribeToGymPlanCommand(String userId, String planId) {
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.planId = Objects.requireNonNull(planId, "planId must not be null");
    }

    public String getUserId() {
        return userId;
    }

    public String getPlanId() {
        return planId;
    }
}