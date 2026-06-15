package com.fitmesh.fitmeshbackend.domain.valueobject;
import com.fitmesh.fitmeshbackend.domain.exception.InvalidPaymentReferenceException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public final class PaymentReference {

    private static final String PREFIX = "FITMESH-PAY";
    private static final int MAX_LENGTH = 50;

    // Updated pattern to accept UUID format
    private static final Pattern FORMAT_PATTERN =
            Pattern.compile("^FITMESH-PAY-[A-Z0-9\\-]+$");

    private final String value;

    private PaymentReference(String value) {
        this.value = normalizeAndValidate(value);
    }

    //FACTORY METHODS

    public static PaymentReference generate(int sequence) {
        // Use UUID for global uniqueness instead of date-based sequence
        String uniquePart = UUID.randomUUID().toString().substring(0, 13).toUpperCase();
        String generated = PREFIX + "-" + uniquePart;

        return new PaymentReference(generated);
    }

    public static PaymentReference of(String rawValue) {
        return new PaymentReference(rawValue);
    }

    //CORE LOGIC

    private String normalizeAndValidate(String rawValue) {
        if (rawValue == null) {
            throw new InvalidPaymentReferenceException("Payment reference cannot be null");
        }

        String normalized = rawValue.trim().toUpperCase();

        if (normalized.isBlank()) {
            throw new InvalidPaymentReferenceException("Payment reference cannot be blank");
        }

        if (normalized.length() > MAX_LENGTH) {
            throw new InvalidPaymentReferenceException(
                    "Payment reference exceeds max length of " + MAX_LENGTH
            );
        }

        if (!FORMAT_PATTERN.matcher(normalized).matches()) {
            throw new InvalidPaymentReferenceException(
                    "Payment reference does not match required format: FITMESH-PAY-XXXXX"
            );
        }

        return normalized;
    }

    // ACCESSORS

    public String getValue() {
        return value;
    }

    //VALUE OBJECT CONTRACT

    @Override
    public boolean equals(Object comparisonObject) {
        if (this == comparisonObject) return true;
        if (!(comparisonObject instanceof PaymentReference)) return false;

        PaymentReference other = (PaymentReference) comparisonObject;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
