package com.fitmesh.fitmeshbackend.application.ports.in.result;

import com.fitmesh.fitmeshbackend.domain.enums.MembershipStatus;

public class SubscriptionResult {

    private final String membershipId;
    private final String paymentId;
    private final String paymentReference;
    private final MembershipStatus membershipStatus;
    private final String redirectUrl;

    public SubscriptionResult(
            String membershipId,
            String paymentId,
            String paymentReference,
            MembershipStatus membershipStatus,
            String redirectUrl
    ) {
        this.membershipId = membershipId;
        this.paymentId = paymentId;
        this.paymentReference = paymentReference;
        this.membershipStatus = membershipStatus;
        this.redirectUrl = redirectUrl;
    }

    // Getters

    public String getMembershipId() {
        return membershipId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public MembershipStatus getMembershipStatus() {
        return membershipStatus;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
