package com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.dto.response;

public class PWAResponse {

    private String status;
    private String message;
    private PWAResponseData data;

    public PWAResponse() {}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PWAResponseData getData() {
        return data;
    }

    public void setData(PWAResponseData data) {
        this.data = data;
    }
}
