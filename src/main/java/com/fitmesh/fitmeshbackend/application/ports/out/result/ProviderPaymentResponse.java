package com.fitmesh.fitmeshbackend.application.ports.out.result;

public class ProviderPaymentResponse {

    private String providerReference;
    private String status;
    private String rawResponse;

    public ProviderPaymentResponse() {
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

    public String getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }
}
