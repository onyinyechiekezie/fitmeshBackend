package com.fitmesh.fitmeshbackend.application.ports.out.command;

import java.math.BigDecimal;

public class ProviderPaymentRequest {

    private String reference;
    private String customerId;
    private BigDecimal amount;
    private String currency;

    public ProviderPaymentRequest() {
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}


