package com.fitmesh.fitmeshbackend.application.ports.in.result;

import com.fitmesh.fitmeshbackend.domain.enums.Currency;
import com.fitmesh.fitmeshbackend.domain.enums.PaymentStatus;
import com.fitmesh.fitmeshbackend.domain.enums.PaymentType;

import java.math.BigDecimal;
import java.time.Instant;

public class PaymentHistoryResult {

    private final String paymentId;
    private final String paymentReference;
    private final BigDecimal amount;
    private final Currency currency;
    private final PaymentType paymentType;
    private final PaymentStatus paymentStatus;
    private final String description;
    private final String providerReference;
    private final Instant createdAt;

    public PaymentHistoryResult(
            String paymentId,
            String paymentReference,
            BigDecimal amount,
            Currency currency,
            PaymentType paymentType,
            PaymentStatus paymentStatus,
            String description,
            String providerReference,
            Instant createdAt
    ) {
        this.paymentId = paymentId;
        this.paymentReference = paymentReference;
        this.amount = amount;
        this.currency = currency;
        this.paymentType = paymentType;
        this.paymentStatus = paymentStatus;
        this.description = description;
        this.providerReference = providerReference;
        this.createdAt = createdAt;
    }

    // Getters

    public String getPaymentId() {
        return paymentId;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public String getDescription() {
        return description;
    }

    public String getProviderReference() {
        return providerReference;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
