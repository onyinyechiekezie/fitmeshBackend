package com.fitmesh.fitmeshbackend.adapter.in.web.dto.response;

import java.time.Instant;

public class InitiatePaymentResponse {

    private final String paymentId;
    private final String reference;
    private final String status;
    private final String redirectUrl;
    private final Instant initiatedAt;

    public InitiatePaymentResponse(
            String paymentId,
            String reference,
            String status,
            String redirectUrl,
            Instant initiatedAt
    ) {
        this.paymentId = paymentId;
        this.reference = reference;
        this.status = status;
        this.redirectUrl = redirectUrl;
        this.initiatedAt = initiatedAt;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getReference() {
        return reference;
    }

    public String getStatus() {
        return status;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public Instant getInitiatedAt() {
        return initiatedAt;
    }
}