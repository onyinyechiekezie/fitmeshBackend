package com.fitmesh.fitmeshbackend.application.ports.in.result;

import com.fitmesh.fitmeshbackend.domain.enums.BillingFrequency;
import com.fitmesh.fitmeshbackend.domain.enums.Currency;

import java.math.BigDecimal;
import java.util.List;

public class GymPlanResult {

    private final String planId;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Currency currency;
    private final BillingFrequency billingFrequency;
    private final int durationInDays;
    private final List<String> features;

    public GymPlanResult(
            String planId,
            String name,
            String description,
            BigDecimal price,
            Currency currency,
            BillingFrequency billingFrequency,
            int durationInDays,
            List<String> features
    ) {
        this.planId = planId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.billingFrequency = billingFrequency;
        this.durationInDays = durationInDays;
        this.features = features;
    }

    // Getters

    public String getPlanId() {
        return planId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BillingFrequency getBillingFrequency() {
        return billingFrequency;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public List<String> getFeatures() {
        return features;
    }
}

