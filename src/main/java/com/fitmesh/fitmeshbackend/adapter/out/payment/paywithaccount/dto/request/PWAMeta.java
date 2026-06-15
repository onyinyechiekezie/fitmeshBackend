package com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.dto.request;

import lombok.Data;

@Data
public class PWAMeta {

    private String type; // single_payment
    private Integer expires_in;
    private Boolean skip_messaging;
    private String biller_code;

    public PWAMeta() {}
}
