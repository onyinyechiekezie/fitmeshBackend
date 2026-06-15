package com.fitmesh.fitmeshbackend.adapter.in.web.dto.response;

import com.fitmesh.fitmeshbackend.domain.enums.MembershipStatus;

import java.time.Instant;

public class MembershipResponse {

    private final String membershipId;
    private final String userId;
    private final String planId;
    private final String planName;
    private final MembershipStatus status;
    private final Instant startDate;
    private final Instant endDate;
    private final Instant lastPaymentDate;
    private final boolean isActive;
    private final boolean isExpired;

    public MembershipResponse(
            String membershipId,
            String userId,
            String planId,
            String planName,
            MembershipStatus status,
            Instant startDate,
            Instant endDate,
            Instant lastPaymentDate,
            boolean isActive,
            boolean isExpired
    ) {
        this.membershipId = membershipId;
        this.userId = userId;
        this.planId = planId;
        this.planName = planName;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lastPaymentDate = lastPaymentDate;
        this.isActive = isActive;
        this.isExpired = isExpired;
    }

    // Getters

    public String getMembershipId() {
        return membershipId;
    }

    public String getUserId() {
        return userId;
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

    public boolean isActive() {
        return isActive;
    }

    public boolean isExpired() {
        return isExpired;
    }
}
