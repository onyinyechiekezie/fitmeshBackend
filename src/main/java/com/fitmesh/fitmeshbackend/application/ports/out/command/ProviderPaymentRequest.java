package com.fitmesh.fitmeshbackend.application.ports.out.command;

import com.fitmesh.fitmeshbackend.domain.enums.Currency;
import com.fitmesh.fitmeshbackend.domain.enums.PaymentType;

import java.math.BigDecimal;
import java.util.Objects;

public class ProviderPaymentRequest {

    private final String reference;
    private final BigDecimal amount;
    private final Currency currency;
    private final PaymentType paymentType;
    private final String customerId;
    private final String customerEmail;
    private final String customerPhone;
    private final String description;
    private final String subscriptionFrequency;
    private final Integer numberOfPayments;

    public ProviderPaymentRequest(
            String reference,
            BigDecimal amount,
            Currency currency,
            PaymentType paymentType,
            String customerId,
            String customerEmail,
            String customerPhone,
            String description,
            String subscriptionFrequency,
            Integer numberOfPayments
    ) {
        this.reference = Objects.requireNonNull(reference, "reference must not be null");
        this.amount = Objects.requireNonNull(amount, "amount must not be null");
        this.currency = Objects.requireNonNull(currency, "currency must not be null");
        this.paymentType = Objects.requireNonNull(paymentType, "paymentType must not be null");
        this.customerId = Objects.requireNonNull(customerId, "customerId must not be null");
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.description = description;
        this.subscriptionFrequency = subscriptionFrequency;
        this.numberOfPayments = numberOfPayments;
    }

    public String getReference() {
        return reference;
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

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
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
}


