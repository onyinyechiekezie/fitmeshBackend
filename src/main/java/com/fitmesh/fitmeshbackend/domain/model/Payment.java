package com.fitmesh.fitmeshbackend.domain.model;

import com.fitmesh.fitmeshbackend.domain.enums.PaymentStatus;
import com.fitmesh.fitmeshbackend.domain.exception.InvalidPaymentStateException;
import com.fitmesh.fitmeshbackend.domain.exception.ProviderReferenceAlreadyAttachedException;
import com.fitmesh.fitmeshbackend.domain.enums.Currency;
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
        this.paymentId = Objects.requireNonNull(paymentId);
        this.paymentReference = Objects.requireNonNull(paymentReference);
        this.amount = Objects.requireNonNull(amount);
        this.currency = Objects.requireNonNull(currency);
        this.paymentType = Objects.requireNonNull(paymentType);
        this.userId = Objects.requireNonNull(userId);
        this.description = description;

        this.paymentStatus = PaymentStatus.PENDING;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public String getProviderReference() {
        return providerReference;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }


    public void attachProviderReference(String providerReference) {
        if (this.providerReference != null) {
            throw new ProviderReferenceAlreadyAttachedException(paymentId);
        }

        this.providerReference = Objects.requireNonNull(
                providerReference,
                "providerReference must not be null"
        );
        touch();
    }

    public void markSuccessful() {
        if (this.paymentStatus != PaymentStatus.PENDING) {
            throw new InvalidPaymentStateException(
                    "Cannot mark payment as SUCCESS when status is " + paymentStatus
            );
        }

        this.paymentStatus = PaymentStatus.SUCCESS;
        touch();
    }

    public void markFailed() {
        if (this.paymentStatus != PaymentStatus.PENDING) {
            throw new InvalidPaymentStateException(
                    "Cannot mark payment as FAILED when status is " + paymentStatus
            );
        }

        this.paymentStatus = PaymentStatus.FAILED;
        touch();
    }



    private void touch() {
        this.updatedAt = Instant.now();
    }

    @Override
    public boolean equals(Object comparisonObject) {
        if (this == comparisonObject) return true;
        if (!(comparisonObject instanceof Payment)) return false;
        Payment other = (Payment) comparisonObject;
        return paymentId.equals(other.paymentId);
    }

    @Override
    public int hashCode() {
        return paymentId.hashCode();
    }
}
