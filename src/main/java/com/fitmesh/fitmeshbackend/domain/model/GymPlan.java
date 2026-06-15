package com.fitmesh.fitmeshbackend.domain.model;

import com.fitmesh.fitmeshbackend.domain.enums.BillingFrequency;
import com.fitmesh.fitmeshbackend.domain.enums.Currency;
import com.fitmesh.fitmeshbackend.domain.exception.InvalidDurationException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GymPlan {

    private final String planId;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Currency currency;
    private final BillingFrequency billingFrequency;
    private final int durationInDays;
    private final List<String> features;

    private boolean isActive;
    private final Instant createdAt;
    private Instant updatedAt;

    // Creation constructor
    public GymPlan(
            String planId,
            String name,
            String description,
            BigDecimal price,
            Currency currency,
            BillingFrequency billingFrequency,
            int durationInDays,
            List<String> features
    ) {
        this.planId = Objects.requireNonNull(planId, "planId must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.description = description;
        this.price = Objects.requireNonNull(price, "price must not be null");
        this.currency = Objects.requireNonNull(currency, "currency must not be null");
        this.billingFrequency = Objects.requireNonNull(
                billingFrequency,
                "billingFrequency must not be null"
        );

        if (durationInDays <= 0) {
            throw new InvalidDurationException("durationInDays must be positive");
        }
        this.durationInDays = durationInDays;

        this.features = features != null ? List.copyOf(features) : Collections.emptyList();
        this.isActive = true;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    // Reconstruction constructor (for repository)
    public GymPlan(
            String planId,
            String name,
            String description,
            BigDecimal price,
            Currency currency,
            BillingFrequency billingFrequency,
            int durationInDays,
            List<String> features,
            boolean isActive,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.planId = Objects.requireNonNull(planId);
        this.name = Objects.requireNonNull(name);
        this.description = description;
        this.price = Objects.requireNonNull(price);
        this.currency = Objects.requireNonNull(currency);
        this.billingFrequency = Objects.requireNonNull(billingFrequency);
        this.durationInDays = durationInDays;
        this.features = features != null ? List.copyOf(features) : Collections.emptyList();
        this.isActive = isActive;
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    // Business methods

    public void activate() {
        this.isActive = true;
        touch();
    }

    public void deactivate() {
        this.isActive = false;
        touch();
    }

    private void touch() {
        this.updatedAt = Instant.now();
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
        return Collections.unmodifiableList(features);
    }

    public boolean isActive() {
        return isActive;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GymPlan)) return false;
        GymPlan gymPlan = (GymPlan) o;
        return planId.equals(gymPlan.planId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planId);
    }
}