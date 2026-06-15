package com.fitmesh.fitmeshbackend.domain.service;

import com.fitmesh.fitmeshbackend.domain.enums.BillingFrequency;
import com.fitmesh.fitmeshbackend.domain.exception.UnsupportedBillingFrequencyException;

public class BillingFrequencyMapper {

    /**
     * Map BillingFrequency to OnePipe subscription frequency
     *
     * OnePipe supports: "daily", "weekly", "monthly"
     *
     * Our mappings:
     * - MONTHLY → "monthly" (1 payment)
     * - QUARTERLY → "monthly" (3 payments)
     * - YEARLY → "monthly" (12 payments)
     */
    public static String toOnePipeFrequency(BillingFrequency billingFrequency) {
        switch (billingFrequency) {
            case MONTHLY:
            case QUARTERLY:
            case YEARLY:
                return "monthly";
            default:
                throw new UnsupportedBillingFrequencyException(billingFrequency.name());
        }
    }

    /**
     * Get number of payments for a billing frequency
     *
     * - MONTHLY → 1 payment (charge once per month, recurring)
     * - QUARTERLY → 3 payments (charge monthly for 3 months)
     * - YEARLY → 12 payments (charge monthly for 12 months)
     */
    public static int getNumberOfPayments(BillingFrequency billingFrequency) {
        switch (billingFrequency) {
            case MONTHLY:
                return 1; // Recurring monthly, indefinitely
            case QUARTERLY:
                return 3; // 3 monthly payments
            case YEARLY:
                return 12; // 12 monthly payments
            default:
                throw new UnsupportedBillingFrequencyException(billingFrequency.name());
        }
    }
}