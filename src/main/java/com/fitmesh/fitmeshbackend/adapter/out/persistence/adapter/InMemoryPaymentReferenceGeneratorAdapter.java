package com.fitmesh.fitmeshbackend.adapter.out.persistence.adapter;

import com.fitmesh.fitmeshbackend.application.ports.out.PaymentReferenceGenerator;
import com.fitmesh.fitmeshbackend.domain.valueobject.PaymentReference;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InMemoryPaymentReferenceGeneratorAdapter implements PaymentReferenceGenerator {

    private final AtomicInteger sequence = new AtomicInteger(0);

    @Override
    public PaymentReference generateNext() {
        int nextSequence = sequence.incrementAndGet();
        return PaymentReference.generate(nextSequence);
    }
}
