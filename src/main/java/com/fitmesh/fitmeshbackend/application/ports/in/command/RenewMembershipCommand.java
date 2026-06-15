package com.fitmesh.fitmeshbackend.application.ports.in.command;

import java.util.Objects;

public class RenewMembershipCommand {

    private final String userId;
    private final String membershipId;

    public RenewMembershipCommand(String userId, String membershipId) {
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.membershipId = Objects.requireNonNull(membershipId, "membershipId must not be null");
    }

    public String getUserId() {
        return userId;
    }

    public String getMembershipId() {
        return membershipId;
    }
}
