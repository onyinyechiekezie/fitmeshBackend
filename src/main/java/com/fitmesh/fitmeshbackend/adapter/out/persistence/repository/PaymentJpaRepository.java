package com.fitmesh.fitmeshbackend.adapter.out.persistence.repository;

import com.fitmesh.fitmeshbackend.adapter.out.persistence.entity.PaymentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentJpaRepository extends JpaRepository<PaymentJpaEntity, String> {

    Optional<PaymentJpaEntity> findByPaymentReference(String paymentReference);
    List<PaymentJpaEntity> findByMembershipId(String membershipId);

    List<PaymentJpaEntity> findByUserId(String userId);
}