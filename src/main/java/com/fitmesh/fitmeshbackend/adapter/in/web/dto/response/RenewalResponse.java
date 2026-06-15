package com.fitmesh.fitmeshbackend.adapter.in.web.dto.response;

public class RenewalResponse {

    private final String membershipId;
    private final String paymentId;
    private final String paymentReference;
    private final String redirectUrl;

    public RenewalResponse(
            String membershipId,
            String paymentId,
            String paymentReference,
            String redirectUrl
    ) {
        this.membershipId = membershipId;
        this.paymentId = paymentId;
        this.paymentReference = paymentReference;
        this.redirectUrl = redirectUrl;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
