package com.fitmesh.fitmeshbackend.adapter.in.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OnePipeWebhookRequest {

    @JsonProperty("transaction_reference")
    private String transactionReference;

    @JsonProperty("provider_reference")
    private String providerReference;

    private String status;
    private String amount;
    private String currency;

    @JsonProperty("transaction_date")
    private String transactionDate;

    @JsonProperty("customer_email")
    private String customerEmail;

    private String description;

    public OnePipeWebhookRequest() {}

    // Getters and Setters

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getProviderReference() {
        return providerReference;
    }

    public void setProviderReference(String providerReference) {
        this.providerReference = providerReference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
