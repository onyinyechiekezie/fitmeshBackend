package com.fitmesh.fitmeshbackend.adapter.out.persistence.adapter;

import com.fitmesh.fitmeshbackend.adapter.out.persistence.entity.PaymentJpaEntity;
import com.fitmesh.fitmeshbackend.adapter.out.persistence.mapper.PaymentEntityMapper;
import com.fitmesh.fitmeshbackend.adapter.out.persistence.repository.PaymentJpaRepository;
import com.fitmesh.fitmeshbackend.application.ports.out.PaymentRepository;
import com.fitmesh.fitmeshbackend.domain.model.Payment;
import com.fitmesh.fitmeshbackend.domain.valueobject.PaymentReference;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaPaymentRepositoryAdapter implements PaymentRepository {

    private final PaymentJpaRepository jpaRepository;
    private final PaymentEntityMapper mapper;

    public JpaPaymentRepositoryAdapter(
            PaymentJpaRepository jpaRepository,
            PaymentEntityMapper mapper
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Payment payment) {
        PaymentJpaEntity entity = mapper.toEntity(payment);
        jpaRepository.save(entity);
    }

    @Override
    public Optional<Payment> findByReference(PaymentReference reference) {
        return jpaRepository.findByPaymentReference(reference.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public List<Payment> findByMembershipId(String membershipId) {
        return jpaRepository.findByMembershipId(membershipId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Payment> findByUserId(String userId) {
        return jpaRepository.findByUserId(userId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Payment> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}