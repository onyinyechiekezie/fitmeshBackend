package com.fitmesh.fitmeshbackend.application.ports.in.command;

import java.math.BigDecimal;

public class InitiatePayWithAccountCommand {

    private String userId;
    private BigDecimal amount;
    private String currency;
    private String planId;

    public InitiatePayWithAccountCommand() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}
