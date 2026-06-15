package com.fitmesh.fitmeshbackend.application.ports.in.result;

import com.fitmesh.fitmeshbackend.domain.enums.MembershipStatus;

import java.time.Instant;

public class AdminMembershipResult {

    private final String membershipId;
    private final String userId;
    private final String userEmail;
    private final String planId;
    private final String planName;
    private final MembershipStatus status;
    private final Instant startDate;
    private final Instant endDate;
    private final Instant lastPaymentDate;
    private final Instant createdAt;

    public AdminMembershipResult(
            String membershipId,
            String userId,
            String userEmail,
            String planId,
            String planName,
            MembershipStatus status,
            Instant startDate,
            Instant endDate,
            Instant lastPaymentDate,
            Instant createdAt
    ) {
        this.membershipId = membershipId;
        this.userId = userId;
        this.userEmail = userEmail;
        this.planId = planId;
        this.planName = planName;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lastPaymentDate = lastPaymentDate;
        this.createdAt = createdAt;
    }

    // Getters

    public String getMembershipId() {
        return membershipId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getPlanId() {
        return planId;
    }

    public String getPlanName() {
        return planName;
    }

    public MembershipStatus getStatus() {
        return status;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public Instant getLastPaymentDate() {
        return lastPaymentDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
