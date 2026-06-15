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
    private final String membershipId;
    private final String description;

    private String providerReference;
    private final String subscriptionFrequency; // "daily", "weekly", "monthly"
    private final Integer numberOfPayments; // Total number of payments for subscription


    private final Instant createdAt;
    private Instant updatedAt;

    // Creation constructor (with membershipId)
    public Payment(
            String paymentId,
            PaymentReference paymentReference,
            BigDecimal amount,
            Currency currency,
            PaymentType paymentType,
            String userId,
            String membershipId,
            String description,
            String subscriptionFrequency,
            Integer numberOfPayments
    ) {
        this.paymentId = Objects.requireNonNull(paymentId);
        this.paymentReference = Objects.requireNonNull(paymentReference);
        this.amount = Objects.requireNonNull(amount);
        this.currency = Objects.requireNonNull(currency);
        this.paymentType = Objects.requireNonNull(paymentType);
        this.userId = Objects.requireNonNull(userId);
        this.membershipId = membershipId; // Can be null for non-membership payments
        this.description = description;
        this.subscriptionFrequency = subscriptionFrequency;
        this.numberOfPayments = numberOfPayments;

        this.paymentStatus = PaymentStatus.PENDING;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    // Reconstruction constructor (for repository)
    public Payment(
            String paymentId,
            PaymentReference paymentReference,
            BigDecimal amount,
            Currency currency,
            PaymentType paymentType,
            PaymentStatus paymentStatus,
            String userId,
            String membershipId,
            String description,
            String providerReference,
            String subscriptionFrequency,
            Integer numberOfPayments,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.paymentId = Objects.requireNonNull(paymentId);
        this.paymentReference = Objects.requireNonNull(paymentReference);
        this.amount = Objects.requireNonNull(amount);
        this.currency = Objects.requireNonNull(currency);
        this.paymentType = Objects.requireNonNull(paymentType);
        this.paymentStatus = Objects.requireNonNull(paymentStatus);
        this.userId = Objects.requireNonNull(userId);
        this.membershipId = membershipId;
        this.description = description;
        this.providerReference = providerReference;
        this.subscriptionFrequency = subscriptionFrequency;
        this.numberOfPayments = numberOfPayments;
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    // GETTERS

    public String getId() {
        return paymentId;
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

    public PaymentReference getPaymentReference() {
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

    public String getUserId() {
        return userId;
    }

    public String getMembershipId() { // NEW GETTER
        return membershipId;
    }

    public String getDescription() {
        return description;
    }

    public String getSubscriptionFrequency() {
        return subscriptionFrequency;
    }

    public Integer getNumberOfPayments() {
        return numberOfPayments;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public boolean isSubscription() {
        return this.paymentType == PaymentType.SUBSCRIPTION;
    }

    // BUSINESS LOGIC

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
        if (this.paymentStatus != PaymentStatus.PENDING &&
                this.paymentStatus != PaymentStatus.REQUESTED) {
            throw new InvalidPaymentStateException(
                    "Cannot mark payment as SUCCESS when status is " + paymentStatus
            );
        }

        this.paymentStatus = PaymentStatus.SUCCESS;
        touch();
    }

    public void markFailed() {
        if (this.paymentStatus != PaymentStatus.PENDING &&
                this.paymentStatus != PaymentStatus.REQUESTED) {
            throw new InvalidPaymentStateException(
                    "Cannot mark payment as FAILED when status is " + paymentStatus
            );
        }

        this.paymentStatus = PaymentStatus.FAILED;
        touch();
    }

    public void markRequested() {
        if (this.paymentStatus != PaymentStatus.PENDING) {
            throw new InvalidPaymentStateException(
                    "Cannot mark payment as REQUESTED when status is " + paymentStatus
            );
        }

        this.paymentStatus = PaymentStatus.REQUESTED;
        touch();
    }

    private void touch() {
        this.updatedAt = Instant.now();
    }

    // VALUE OBJECT CONTRACT

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