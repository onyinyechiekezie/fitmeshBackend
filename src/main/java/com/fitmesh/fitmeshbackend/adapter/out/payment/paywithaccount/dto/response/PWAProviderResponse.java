package com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PWAProviderResponse {

    private String reference;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("currency_code")
    private String currencyCode;

    @JsonProperty("customer_email")
    private String customerEmail;

    @JsonProperty("bank_code")
    private String bankCode;

    private String status;

    @JsonProperty("created_on")
    private String createdOn;

    private PWAProviderMeta meta;

    public PWAProviderResponse() {}

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public PWAProviderMeta getMeta() {
        return meta;
    }

    public void setMeta(PWAProviderMeta meta) {
        this.meta = meta;
    }
}
