package com.fitmesh.fitmeshbackend.application.ports.in.command;

import com.fitmesh.fitmeshbackend.domain.exception.InvalidWebhookDataException;

import java.util.Objects;

public class ProcessPaymentWebhookCommand {

    private final String transactionReference;
    private final String providerReference;
    private final String status;

    public ProcessPaymentWebhookCommand(
            String transactionReference,
            String providerReference,
            String status
    ) {
        if (transactionReference == null || transactionReference.isBlank()) {
            throw new InvalidWebhookDataException("transaction_reference cannot be null or blank");
        }
        if (providerReference == null || providerReference.isBlank()) {
            throw new InvalidWebhookDataException("provider_reference cannot be null or blank");
        }
        if (status == null || status.isBlank()) {
            throw new InvalidWebhookDataException("status cannot be null or blank");
        }

        this.transactionReference = transactionReference;
        this.providerReference = providerReference;
        this.status = status;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public String getProviderReference() {
        return providerReference;
    }

    public String getStatus() {
        return status;
    }
}
