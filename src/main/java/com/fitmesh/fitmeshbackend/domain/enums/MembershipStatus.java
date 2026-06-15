package com.fitmesh.fitmeshbackend.domain.enums;

public enum MembershipStatus {
    PENDING,    // Created but not activated (waiting for payment)
    ACTIVE,     // Currently active (paid and within validity period)
    PAUSED,     // Temporarily paused by user
    EXPIRED,    // End date has passed
    CANCELLED   // Cancelled by user or admin
}
