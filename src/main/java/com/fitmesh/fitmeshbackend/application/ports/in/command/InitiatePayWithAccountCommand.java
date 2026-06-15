package com.fitmesh.fitmeshbackend.application.ports.in.command;

import com.fitmesh.fitmeshbackend.domain.enums.Currency;
import com.fitmesh.fitmeshbackend.domain.enums.PaymentType;

import java.math.BigDecimal;
import java.util.Objects;

public class InitiatePayWithAccountCommand {

    private final String userId;
    private final BigDecimal amount;
    private final Currency currency;
    private final PaymentType paymentType;
    private final String description;

    public InitiatePayWithAccountCommand(
            String userId,
            BigDecimal amount,
            Currency currency,
            PaymentType paymentType,
            String description
    ) {
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.amount = Objects.requireNonNull(amount, "amount must not be null");
        this.currency = Objects.requireNonNull(currency, "currency must not be null");
        this.paymentType = Objects.requireNonNull(paymentType, "paymentType must not be null");
        this.description = description;
    }

    public String getUserId() {
        return userId;
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

    public String getDescription() {
        return description;
    }
}
