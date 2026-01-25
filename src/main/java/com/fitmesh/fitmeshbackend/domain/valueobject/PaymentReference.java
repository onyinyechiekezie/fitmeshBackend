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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public final class PaymentReference {

    private static final String PREFIX = "FITMESH-PAY";
    private static final int MAX_LENGTH = 50;

    private static final Pattern FORMAT_PATTERN =
            Pattern.compile("^FITMESH-PAY-\\d{8}-\\d+$");

    private final String value;

    private PaymentReference(String value) {
        this.value = normalizeAndValidate(value);
    }

    //FACTORY METHODS

//    public static PaymentReference generate(int sequence) {
//        String datePart = LocalDate.now()
//                .format(DateTimeFormatter.BASIC_ISO_DATE); // yyyyMMdd
//
//        String generated =
//                PREFIX + "-" + datePart + "-" + sequence;
//
//        return new PaymentReference(generated);
//    }

    public static PaymentReference generate(int sequence) {
        String datePart = LocalDate.now()
                .format(DateTimeFormatter.BASIC_ISO_DATE);

        String generated =
                PREFIX + "-" + datePart + "-" + String.format("%03d", sequence);

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
                    "Payment reference does not match required format: FITMESH-PAY-YYYYMMDD-SEQ"
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
