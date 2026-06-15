package com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
@Data
public class PWATransactionRequest {

    private String mock_mode; // Inspect
    private String transaction_ref;
    private String transaction_desc;
    private String transaction_ref_parent;
    private BigDecimal amount;
    private PWACustomer customer;
    private PWAMeta meta;
    private Map<String, Object> details = new HashMap<>();

    public PWATransactionRequest() {}
}
