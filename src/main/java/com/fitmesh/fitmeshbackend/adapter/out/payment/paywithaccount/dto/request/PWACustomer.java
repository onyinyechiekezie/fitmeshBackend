package com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.dto.request;

import lombok.Data;

@Data
public class PWACustomer {

    private String customer_ref;
    private String firstname;
    private String surname;
    private String email;
    private String mobile_no;

    public PWACustomer() {}
}
