package com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.mapper;

import com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.dto.request.PWASendInvoiceRequest;
import com.fitmesh.fitmeshbackend.application.ports.out.command.ProviderPaymentRequest;
import com.fitmesh.fitmeshbackend.domain.enums.PaymentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentToPwaSendInvoiceRequestMapper {

    private final String billerCode;

    public PaymentToPwaSendInvoiceRequestMapper(
            @Value("${onepipe.biller-code}") String billerCode
    ) {
        this.billerCode = billerCode;
    }

    public PWASendInvoiceRequest map(ProviderPaymentRequest request) {
        PWASendInvoiceRequest pwaRequest = new PWASendInvoiceRequest();

        // Root level fields
        pwaRequest.setRequestRef(request.getReference());
        pwaRequest.setRequestType("send invoice");

        // Auth
        PWASendInvoiceRequest.Auth auth = new PWASendInvoiceRequest.Auth();
        auth.setType(null);
        auth.setSecure(null);
        auth.setAuthProvider("PaywithAccount");
        pwaRequest.setAuth(auth);

        // Transaction
        PWASendInvoiceRequest.Transaction transaction = new PWASendInvoiceRequest.Transaction();
        transaction.setMockMode("Inspect"); // Use "Live" in production
        transaction.setTransactionRef(request.getReference());
        transaction.setTransactionDesc(
                request.getDescription() != null
                        ? request.getDescription()
                        : "Payment for FitMesh service"
        );
        transaction.setTransactionRefParent(null);
        transaction.setAmount(request.getAmount());

        // Customer
        PWASendInvoiceRequest.Customer customer = new PWASendInvoiceRequest.Customer();
        customer.setCustomerRef(request.getCustomerPhone());
        customer.setFirstname(""); // Optional - can extract from User if needed
        customer.setSurname(""); // Optional
        customer.setEmail(request.getCustomerEmail());
        customer.setMobileNo(request.getCustomerPhone());
        transaction.setCustomer(customer);

        // Meta
        PWASendInvoiceRequest.Meta meta = new PWASendInvoiceRequest.Meta();

        if (request.getPaymentType() == PaymentType.SINGLE_PAYMENT) {
            meta.setType("single_payment");
            meta.setExpiresIn(30); // 30 minutes
            meta.setSkipMessaging(false);
            meta.setBillerCode(billerCode);
        } else if (request.getPaymentType() == PaymentType.SUBSCRIPTION) {
            meta.setType("subscription");
            meta.setBillerCode(billerCode);
            // TODO: Add subscription-specific fields when we implement subscriptions
            meta.setRepeatFrequency(request.getSubscriptionFrequency());
            meta.setNumber_of_payments(request.getNumberOfPayments());
            // meta.setRepeatFrequency(...)
            // meta.setRepeatStartDate(...)
            // meta.setRepeatEndDate(...)
        }

        transaction.setMeta(meta);

        pwaRequest.setTransaction(transaction);

        return pwaRequest;
    }
}