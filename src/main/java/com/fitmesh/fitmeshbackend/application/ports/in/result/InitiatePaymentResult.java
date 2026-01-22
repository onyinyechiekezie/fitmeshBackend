package com.fitmesh.fitmeshbackend.application.ports.in.result;

public class InitiatePaymentResult {

    private String paymentId;
    private String reference;
    private String providerStatus;

    public InitiatePaymentResult() {
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getProviderStatus() {
        return providerStatus;
    }

    public void setProviderStatus(String providerStatus) {
        this.providerStatus = providerStatus;
    }
}
