package com.fitmesh.fitmeshbackend.application.ports.out.result;

import java.util.Objects;

public class ProviderPaymentResponse {

    private final String providerReference;
    private final String status;
    private final String redirectUrl;
    private final String message;

    public ProviderPaymentResponse(
            String providerReference,
            String status,
            String redirectUrl,
            String message
    ) {
        this.providerReference = Objects.requireNonNull(
                providerReference,
                "providerReference must not be null"
        );
        this.status = Objects.requireNonNull(status, "status must not be null");
        this.redirectUrl = redirectUrl;
        this.message = message;
    }

    public String getProviderReference() {
        return providerReference;
    }

    public String getStatus() {
        return status;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getMessage() {
        return message;
    }
}

   