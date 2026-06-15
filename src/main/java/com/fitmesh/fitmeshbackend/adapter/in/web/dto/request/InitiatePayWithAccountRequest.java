package com.fitmesh.fitmeshbackend.adapter.in.web.dto.request;

import com.fitmesh.fitmeshbackend.domain.enums.Currency;
import com.fitmesh.fitmeshbackend.domain.enums.PaymentType;

import java.math.BigDecimal;

public class InitiatePayWithAccountRequest {

    private BigDecimal amount;
    private Currency currency;
    private PaymentType paymentType;
    private String description;

    public InitiatePayWithAccountRequest() {}

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