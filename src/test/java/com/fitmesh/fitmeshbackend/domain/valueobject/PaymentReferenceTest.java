package com.fitmesh.fitmeshbackend.domain.valueobject;

import com.fitmesh.fitmeshbackend.domain.exception.InvalidPaymentReferenceException;
import com.fitmesh.fitmeshbackend.domain.valueobject.PaymentReference;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentReferenceTest {

    @Test
    void shouldGenerateValidPaymentReference() {
        PaymentReference reference = PaymentReference.generate(1);

        assertNotNull(reference);
        assertTrue(reference.getValue().startsWith("FITMESH-PAY-"));
    }

    @Test
    void shouldNormalizeLowercaseAndWhitespace() {
        PaymentReference reference =
                PaymentReference.of("  fitmesh-pay-20240901-10 ");

        assertEquals(
                "FITMESH-PAY-20240901-10",
                reference.getValue()
        );
    }

    @Test
    void shouldFailWhenNull() {
        assertThrows(
                InvalidPaymentReferenceException.class,
                () -> PaymentReference.of(null)
        );
    }

    @Test
    void shouldFailWhenBlank() {
        assertThrows(
                InvalidPaymentReferenceException.class,
                () -> PaymentReference.of("   ")
        );
    }

    @Test
    void shouldFailForInvalidFormat() {
        assertThrows(
                InvalidPaymentReferenceException.class,
                () -> PaymentReference.of("FITMESH-001")
        );
    }

    @Test
    void shouldFailForIllegalCharacters() {
        assertThrows(
                InvalidPaymentReferenceException.class,
                () -> PaymentReference.of("FITMESH-PAY-20240901-01#")
        );
    }

    @Test
    void shouldFailWhenTooLong() {
        String longValue = "FITMESH-PAY-20240901-" + "1".repeat(100);

        assertThrows(
                InvalidPaymentReferenceException.class,
                () -> PaymentReference.of(longValue)
        );
    }

    @Test
    void referencesWithSameValueShouldBeEqual() {
        PaymentReference ref1 =
                PaymentReference.of("FITMESH-PAY-20240901-1");
        PaymentReference ref2 =
                PaymentReference.of("fitmesh-pay-20240901-1");

        assertEquals(ref1, ref2);
        assertEquals(ref1.hashCode(), ref2.hashCode());
    }
}
