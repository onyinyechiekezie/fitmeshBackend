//package com.fitmesh.fitmeshbackend.domain.model;
//
//import com.fitmesh.fitmeshbackend.domain.enums.Currency;
//import com.fitmesh.fitmeshbackend.domain.enums.PaymentStatus;
//import com.fitmesh.fitmeshbackend.domain.enums.PaymentType;
//import com.fitmesh.fitmeshbackend.domain.exception.InvalidPaymentStateException;
//import com.fitmesh.fitmeshbackend.domain.exception.ProviderReferenceAlreadyAttachedException;
//import com.fitmesh.fitmeshbackend.domain.valueobject.PaymentReference;
//import org.junit.jupiter.api.Test;
//
//import java.math.BigDecimal;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class PaymentTest {
//
//    private Payment createValidPayment() {
//        return new Payment(
//                "payment-id-123",
//                PaymentReference.generate(1),
//                BigDecimal.valueOf(5000),
//                Currency.NGN,
//                PaymentType.SINGLE_PAYMENT,
//                "user-123",
//                "Gym single payment"
//        );
//    }
//
//    @Test
//    void payment_should_start_in_pending_state() {
//        Payment payment = createValidPayment();
//        assertEquals(PaymentStatus.PENDING, payment.getPaymentStatus());
//    }
//
//    @Test
//    void should_attach_provider_reference_successfully() {
//        Payment payment = createValidPayment();
//        payment.attachProviderReference("PWA-REF-123");
//        assertNotNull(payment.getProviderReference());
//    }
//
//    @Test
//    void should_not_allow_provider_reference_to_be_attached_twice() {
//        Payment payment = createValidPayment();
//        payment.attachProviderReference("PWA-REF-123");
//
//        assertThrows(
//                ProviderReferenceAlreadyAttachedException.class,
//                () -> payment.attachProviderReference("PWA-REF-456")
//        );
//    }
//
//    @Test
//    void should_reject_null_provider_reference() {
//        Payment payment = createValidPayment();
//
//        assertThrows(
//                NullPointerException.class,
//                () -> payment.attachProviderReference(null)
//        );
//    }
//
//    @Test
//    void should_mark_payment_successful_from_pending() {
//        Payment payment = createValidPayment();
//        payment.markSuccessful();
//        assertEquals(PaymentStatus.SUCCESS, payment.getPaymentStatus());
//    }
//
//    @Test
//    void should_not_allow_marking_success_twice() {
//        Payment payment = createValidPayment();
//        payment.markSuccessful();
//
//        assertThrows(
//                InvalidPaymentStateException.class,
//                payment::markSuccessful
//        );
//    }
//
//    @Test
//    void should_not_allow_marking_failed_after_success() {
//        Payment payment = createValidPayment();
//        payment.markSuccessful();
//
//        assertThrows(
//                InvalidPaymentStateException.class,
//                payment::markFailed
//        );
//    }
//
//    @Test
//    void should_not_allow_marking_success_after_failed() {
//        Payment payment = createValidPayment();
//        payment.markFailed();
//
//        assertThrows(
//                InvalidPaymentStateException.class,
//                payment::markSuccessful
//        );
//    }
//
//    @Test
//    void updated_at_should_change_when_payment_is_marked_successful() {
//        Payment payment = createValidPayment();
//        var before = payment.getUpdatedAt();
//
//        payment.markSuccessful();
//
//        var after = payment.getUpdatedAt();
//        assertTrue(!after.isBefore(before));
//    }
//
//    @Test
//    void updated_at_should_change_when_provider_reference_is_attached() {
//        Payment payment = createValidPayment();
//        var before = payment.getUpdatedAt();
//
//        payment.attachProviderReference("PWA-REF-123");
//
//        var after = payment.getUpdatedAt();
//        assertTrue(!after.isBefore(before));
//    }
//
//
//    @Test
//    void payments_with_same_id_should_be_equal() {
//        PaymentReference ref = PaymentReference.generate(1);
//
//        Payment p1 = new Payment(
//                "same-id",
//                ref,
//                BigDecimal.valueOf(1000),
//                Currency.NGN,
//                PaymentType.SINGLE_PAYMENT,
//                "user-1",
//                "desc"
//        );
//
//        Payment p2 = new Payment(
//                "same-id",
//                ref,
//                BigDecimal.valueOf(2000),
//                Currency.USD,
//                PaymentType.SUBSCRIPTION,
//                "user-2",
//                "desc2"
//        );
//
//        assertEquals(p1, p2);
//        assertEquals(p1.hashCode(), p2.hashCode());
//    }
//
//    @Test
//    void should_not_allow_null_payment_reference() {
//        assertThrows(
//                NullPointerException.class,
//                () -> new Payment(
//                        "id",
//                        null,
//                        BigDecimal.valueOf(1000),
//                        Currency.NGN,
//                        PaymentType.SINGLE_PAYMENT,
//                        "user",
//                        "desc"
//                )
//        );
//    }
//}
