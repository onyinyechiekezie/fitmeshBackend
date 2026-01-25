package com.fitmesh.fitmeshbackend.domain.model;

import com.fitmesh.fitmeshbackend.domain.enums.Currency;
import com.fitmesh.fitmeshbackend.domain.enums.PaymentStatus;
import com.fitmesh.fitmeshbackend.domain.enums.PaymentType;
import com.fitmesh.fitmeshbackend.domain.valueobject.PaymentReference;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Payment {

    private final String paymentId;
    private final PaymentReference paymentReference;

    private final BigDecimal amount;
    private final Currency currency;

    private final PaymentType paymentType;
    private PaymentStatus paymentStatus;

    private final String userId;
    private final String description;

    private String providerReference;

    private final Instant createdAt;
    private Instant updatedAt;

    public Payment(
            String paymentId,
            PaymentReference paymentReference,
            BigDecimal amount,
            Currency currency,
            PaymentType paymentType,
            String userId,
            String description
    ) {
        this.paymentId = Objects.requireNonNull(paymentId, "paymentId must not be null");
        this.paymentReference = Objects.requireNonNull(paymentReference, "paymentReference must not be null");

        this.amount = Objects.requireNonNull(amount, "amount must not be null");
        this.currency = Objects.requireNonNull(currency, "currency must not be null");

        this.paymentType = Objects.requireNonNull(paymentType, "paymentType must not be null");
        this.paymentStatus = PaymentStatus.PENDING;

        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.description = description;

        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public void attachProviderReference(String providerReference) {
        this.providerReference = Objects.requireNonNull(
                providerReference,
                "providerReference must not be null"
        );
        touch();
    }

    public void markSuccessful() {
        this.paymentStatus = PaymentStatus.SUCCESS;
        touch();
    }

    public void markFailed() {
        this.paymentStatus = PaymentStatus.FAILED;
        touch();
    }

    private void touch() {
        this.updatedAt = Instant.now();
    }

    @Override
    public boolean equals(Object comparisonObject) {
        if (this == comparisonObject) return true;
        if (comparisonObject == null || getClass() != comparisonObject.getClass()) return false;

        Payment otherPayment = (Payment) comparisonObject;
        return paymentId.equals(otherPayment.paymentId);
    }

    @Override
    public int hashCode() {
        return paymentId.hashCode();
    }
}
