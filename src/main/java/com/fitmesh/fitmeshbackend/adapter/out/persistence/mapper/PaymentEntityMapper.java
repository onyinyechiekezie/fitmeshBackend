package com.fitmesh.fitmeshbackend.adapter.out.persistence.mapper;

import com.fitmesh.fitmeshbackend.adapter.out.persistence.entity.PaymentJpaEntity;
import com.fitmesh.fitmeshbackend.domain.enums.Currency;
import com.fitmesh.fitmeshbackend.domain.enums.PaymentStatus;
import com.fitmesh.fitmeshbackend.domain.enums.PaymentType;
import com.fitmesh.fitmeshbackend.domain.model.Payment;
import com.fitmesh.fitmeshbackend.domain.valueobject.PaymentReference;
import org.springframework.stereotype.Component;

@Component
public class PaymentEntityMapper {

    /**
     * Convert domain Payment to JPA entity
     */
    public PaymentJpaEntity toEntity(Payment payment) {
        PaymentJpaEntity entity = new PaymentJpaEntity();

        entity.setPaymentId(payment.getId());
        entity.setPaymentReference(payment.getPaymentReference().getValue());
        entity.setAmount(payment.getAmount());
        entity.setCurrency(payment.getCurrency().name());
        entity.setPaymentType(payment.getPaymentType().name());
        entity.setPaymentStatus(payment.getPaymentStatus().name());
        entity.setUserId(payment.getUserId());
        entity.setMembershipId(payment.getMembershipId());
        entity.setDescription(payment.getDescription());
        entity.setProviderReference(payment.getProviderReference());
        entity.setSubscriptionFrequency(payment.getSubscriptionFrequency());
        entity.setNumberOfPayments(payment.getNumberOfPayments());
        entity.setCreatedAt(payment.getCreatedAt());
        entity.setUpdatedAt(payment.getUpdatedAt());

        return entity;
    }

    /**
     * Convert JPA entity to domain Payment
     */
    public Payment toDomain(PaymentJpaEntity entity) {
        // Use reconstruction constructor
        return new Payment(
                entity.getPaymentId(),
                PaymentReference.of(entity.getPaymentReference()),
                entity.getAmount(),
                Currency.valueOf(entity.getCurrency()),
                PaymentType.valueOf(entity.getPaymentType()),
                PaymentStatus.valueOf(entity.getPaymentStatus()),
                entity.getUserId(),
                entity.getMembershipId(),
                entity.getDescription(),
                entity.getProviderReference(),
                entity.getSubscriptionFrequency(),
                entity.getNumberOfPayments(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}