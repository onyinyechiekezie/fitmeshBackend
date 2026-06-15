package com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PWAProviderMeta {

    @JsonProperty("payment_id")
    private Long paymentId;

    @JsonProperty("virtual_account_name")
    private String virtualAccountName;

    @JsonProperty("virtual_account_number")
    private String virtualAccountNumber;

    @JsonProperty("virtual_account_bank_name")
    private String virtualAccountBankName;

    @JsonProperty("virtual_account_bank_code")
    private String virtualAccountBankCode;

    @JsonProperty("virtual_account_status")
    private String virtualAccountStatus;

    @JsonProperty("virtual_account_expiry_date")
    private String virtualAccountExpiryDate;

    @JsonProperty("ussd_code")
    private String ussdCode;

    @JsonProperty("existing_subscription")
    private Boolean existingSubscription;

    @JsonProperty("activation_url")
    private String activationUrl;

    public PWAProviderMeta() {}

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getVirtualAccountName() {
        return virtualAccountName;
    }

    public void setVirtualAccountName(String virtualAccountName) {
        this.virtualAccountName = virtualAccountName;
    }

    public String getVirtualAccountNumber() {
        return virtualAccountNumber;
    }

    public void setVirtualAccountNumber(String virtualAccountNumber) {
        this.virtualAccountNumber = virtualAccountNumber;
    }

    public String getVirtualAccountBankName() {
        return virtualAccountBankName;
    }

    public void setVirtualAccountBankName(String virtualAccountBankName) {
        this.virtualAccountBankName = virtualAccountBankName;
    }

    public String getVirtualAccountBankCode() {
        return virtualAccountBankCode;
    }

    public void setVirtualAccountBankCode(String virtualAccountBankCode) {
        this.virtualAccountBankCode = virtualAccountBankCode;
    }

    public String getVirtualAccountStatus() {
        return virtualAccountStatus;
    }

    public void setVirtualAccountStatus(String virtualAccountStatus) {
        this.virtualAccountStatus = virtualAccountStatus;
    }

    public String getVirtualAccountExpiryDate() {
        return virtualAccountExpiryDate;
    }

    public void setVirtualAccountExpiryDate(String virtualAccountExpiryDate) {
        this.virtualAccountExpiryDate = virtualAccountExpiryDate;
    }

    public String getUssdCode() {
        return ussdCode;
    }

    public void setUssdCode(String ussdCode) {
        this.ussdCode = ussdCode;
    }

    public Boolean getExistingSubscription() {
        return existingSubscription;
    }

    public void setExistingSubscription(Boolean existingSubscription) {
        this.existingSubscription = existingSubscription;
    }

    public String getActivationUrl() {
        return activationUrl;
    }

    public void setActivationUrl(String activationUrl) {
        this.activationUrl = activationUrl;
    }
}