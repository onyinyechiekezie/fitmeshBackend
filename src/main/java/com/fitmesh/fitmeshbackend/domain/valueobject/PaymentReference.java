package com.fitmesh.fitmeshbackend.domain.valueobject;
//
//import java.util.Objects;
//
//public final class PaymentReference {
//
//    private final String value;
//
//    private PaymentReference(String value) {
//        this.value = value;
//    }
//
//    /**
//     * Factory for creating a new internal reference
//     */
//    public static PaymentReference generate() {
//        return new PaymentReference("FITMESH-" + UUID.randomUUID());
//    }
//
//    /**
//     * Factory for restoring from persistence or provider callback
//     */
//    public static PaymentReference of(String value) {
//        if (value == null || value.isBlank()) {
//            throw new IllegalArgumentException("Payment reference cannot be null or blank");
//        }
//        return new PaymentReference(value);
//    }
//
//    public String value() {
//        return value;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof PaymentReference)) return false;
//        PaymentReference that = (PaymentReference) o;
//        return value.equals(that.value);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(value);
//    }
//
//    @Override
//    public String toString() {
//        return value;
//    }
//}

import com.fitmesh.fitmeshbackend.domain.exception.InvalidPaymentReferenceException;

import java.util.Objects;
import java.util.UUID;

public final class PaymentReference {

    private final String referenceValue;

    private PaymentReference(String referenceValue) {
        validate(referenceValue);
        this.referenceValue = referenceValue;
    }

    public static PaymentReference generate() {
        return new PaymentReference(UUID.randomUUID().toString());
    }

    public static PaymentReference of(String referenceValue) {
        return new PaymentReference(referenceValue);
    }

    public String getValue() {
        return referenceValue;
    }

    private void validate(String referenceValue) {
        if (referenceValue == null || referenceValue.isBlank()) {
            throw new InvalidPaymentReferenceException("reference cannot be null or blank");
        }
    }

    @Override
    public boolean equals(Object comparedObject) {
        if (this == comparedObject) {
            return true;
        }

        if (!(comparedObject instanceof PaymentReference)) {
            return false;
        }

        PaymentReference otherPaymentReference = (PaymentReference) comparedObject;
        return referenceValue.equals(otherPaymentReference.referenceValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(referenceValue);
    }

    @Override
    public String toString() {
        return referenceValue;
    }
}