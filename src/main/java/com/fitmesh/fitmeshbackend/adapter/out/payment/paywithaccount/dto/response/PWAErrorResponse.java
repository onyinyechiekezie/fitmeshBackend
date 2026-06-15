package com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.dto.response;

import lombok.Data;

@Data
public class PWAErrorResponse {

    private String code;
    private String message;

    public PWAErrorResponse() {}
}
