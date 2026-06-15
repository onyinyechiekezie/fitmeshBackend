package com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.dto.request;

import lombok.Data;

@Data
public class PWAAuth {

    private String type;
    private String secure;
    private String auth_provider;

    public PWAAuth() {}

    public PWAAuth(String type, String secure, String authProvider) {
        this.type = type;
        this.secure = secure;
        this.auth_provider = authProvider;
    }
}
