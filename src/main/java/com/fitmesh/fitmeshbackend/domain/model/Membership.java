package com.fitmesh.fitmeshbackend.domain.model;

import com.fitmesh.fitmeshbackend.domain.enums.MembershipStatus;
import com.fitmesh.fitmeshbackend.domain.exception.InvalidMembershipStateException;

import java.time.Instant;
import java.util.Objects;

public class Membership {

    private final String membershipId;
    private final String userId;
    private final String planId;

    private MembershipStatus status;
    private Instant startDate;
    private Instant endDate;
    private Instant lastPaymentDate;

    private final Instant createdAt;
    private Instant updatedAt;

    // Creation constructor
    public Membership(
            String membershipId,
            String userId,
            String planId
    ) {
        this.membershipId = Objects.requireNonNull(
                membershipId,
                "membershipId must not be null"
        );
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.planId = Objects.requireNonNull(planId, "planId must not be null");

        this.status = MembershipStatus.PENDING;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    // Reconstruction constructor
    public Membership(
            String membershipId,
            String userId,
            String planId,
            MembershipStatus status,
            Instant startDate,
            Instant endDate,
            Instant lastPaymentDate,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.membershipId = Objects.requireNonNull(membershipId);
        this.userId = Objects.requireNonNull(userId);
        this.planId = Objects.requireNonNull(planId);
        this.status = Objects.requireNonNull(status);
        this.startDate = startDate;
        this.endDate = endDate;
        this.lastPaymentDate = lastPaymentDate;
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    // Business methods

    public void activate(int durationInDays) {
        if (this.status == MembershipStatus.CANCELLED) {
            throw new InvalidMembershipStateException("Cannot activate a cancelled membership");
        }

        this.status = MembershipStatus.ACTIVE;
        this.startDate = Instant.now();
        this.endDate = this.startDate.plusSeconds(durationInDays * 24L * 60L * 60L);
        this.lastPaymentDate = Instant.now();
        touch();
    }

    public void renew(int durationInDays) {
        if (this.status != MembershipStatus.ACTIVE && this.status != MembershipStatus.EXPIRED) {
            throw new InvalidMembershipStateException(
                    "Can only renew ACTIVE or EXPIRED memberships"
            );
        }

        Instant now = Instant.now();

        // If expired, start fresh from now
        if (this.status == MembershipStatus.EXPIRED || this.endDate.isBefore(now)) {
            this.startDate = now;
            this.endDate = now.plusSeconds(durationInDays * 24L * 60L * 60L);
        } else {
            // If still active, extend from current end date
            this.endDate = this.endDate.plusSeconds(durationInDays * 24L * 60L * 60L);
        }

        this.status = MembershipStatus.ACTIVE;
        this.lastPaymentDate = now;
        touch();
    }

    public void pause() {
        if (this.status != MembershipStatus.ACTIVE) {
            throw new InvalidMembershipStateException("Can only pause ACTIVE memberships");
        }

        this.status = MembershipStatus.PAUSED;
        touch();
    }

    public void resume() {
        if (this.status != MembershipStatus.PAUSED) {
            throw new InvalidMembershipStateException("Can only resume PAUSED memberships");
        }

        this.status = MembershipStatus.ACTIVE;
        touch();
    }

    public void cancel() {
        if (this.status == MembershipStatus.CANCELLED) {
            throw new InvalidMembershipStateException("Membership is already cancelled");
        }

        this.status = MembershipStatus.CANCELLED;
        touch();
    }

    public void markAsExpired() {
        if (this.status == MembershipStatus.ACTIVE) {
            this.status = MembershipStatus.EXPIRED;
            touch();
        }
    }

    public boolean isExpired() {
        return this.endDate != null && this.endDate.isBefore(Instant.now());
    }

    public boolean isActive() {
        return this.status == MembershipStatus.ACTIVE && !isExpired();
    }

    private void touch() {
        this.updatedAt = Instant.now();
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

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Membership)) return false;
        Membership that = (Membership) o;
        return membershipId.equals(that.membershipId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(membershipId);
    }
}