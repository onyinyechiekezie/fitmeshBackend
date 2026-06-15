package com.fitmesh.fitmeshbackend.application.ports.in.result;

import java.time.Instant;

public class InitiatePaymentResult {

    private String paymentId;
    private String paymentReference;
    private String providerStatus;
    private String redirectUrl;
    private Instant initiatedAt;

    public InitiatePaymentResult(
            String paymentId,
            String paymentReference,
            String providerStatus,
            String redirectUrl,
            Instant initiatedAt
    ) {
        this.paymentId = paymentId;
        this.paymentReference = paymentReference;
        this.providerStatus = providerStatus;
        this.redirectUrl = redirectUrl;
        this.initiatedAt = initiatedAt;
    }

    public InitiatePaymentResult() {
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public String getProviderStatus() {
        return providerStatus;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public Instant getInitiatedAt() {
        return initiatedAt;
    }
}
