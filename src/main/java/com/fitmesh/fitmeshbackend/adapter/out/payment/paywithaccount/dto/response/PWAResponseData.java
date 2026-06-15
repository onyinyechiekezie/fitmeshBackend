package com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PWAResponseData {

    private String providerResponseCode;
    private String provider;
    private List<PWAErrorResponse> errors;
    private PWAErrorResponse error;

    @JsonProperty("provider_response")
    private PWAProviderResponse providerResponse;

    public PWAResponseData() {}

    public String getProviderResponseCode() {
        return providerResponseCode;
    }

    public void setProviderResponseCode(String providerResponseCode) {
        this.providerResponseCode = providerResponseCode;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public List<PWAErrorResponse> getErrors() {
        return errors;
    }

    public void setErrors(List<PWAErrorResponse> errors) {
        this.errors = errors;
    }

    public PWAErrorResponse getError() {
        return error;
    }

    public void setError(PWAErrorResponse error) {
        this.error = error;
    }

    public PWAProviderResponse getProviderResponse() {
        return providerResponse;
    }

    public void setProviderResponse(PWAProviderResponse providerResponse) {
        this.providerResponse = providerResponse;
    }
}
