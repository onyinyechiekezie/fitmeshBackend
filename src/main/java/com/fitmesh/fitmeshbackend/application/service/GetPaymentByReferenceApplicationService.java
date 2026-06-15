package com.fitmesh.fitmeshbackend.application.service;

import com.fitmesh.fitmeshbackend.application.ports.in.GetPaymentByReferenceUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.result.PaymentResult;
import com.fitmesh.fitmeshbackend.application.ports.out.PaymentRepository;
import com.fitmesh.fitmeshbackend.domain.exception.PaymentNotFoundException;
import com.fitmesh.fitmeshbackend.domain.model.Payment;
import com.fitmesh.fitmeshbackend.domain.valueobject.PaymentReference;
import org.springframework.stereotype.Service;

@Service
public class GetPaymentByReferenceApplicationService
        implements GetPaymentByReferenceUseCase {

    private final PaymentRepository paymentRepository;

    public GetPaymentByReferenceApplicationService(
            PaymentRepository paymentRepository
    ) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentResult getByReference(String reference) {
        PaymentReference paymentReference = PaymentReference.of(reference);

        Payment payment = paymentRepository.findByReference(paymentReference)
                .orElseThrow(() -> new PaymentNotFoundException(reference));

        return new PaymentResult(
                payment.getId(),
                payment.getPaymentReference().getValue(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getPaymentType(),
                payment.getPaymentStatus(),
                payment.getUserId(),
                payment.getDescription(),
                payment.getProviderReference(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }
}