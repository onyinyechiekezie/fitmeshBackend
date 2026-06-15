package com.fitmesh.fitmeshbackend.application.ports.out;

import com.fitmesh.fitmeshbackend.domain.model.Payment;
import com.fitmesh.fitmeshbackend.domain.valueobject.PaymentReference;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {

    void save(Payment payment);
    Optional<Payment> findByReference(PaymentReference reference);
    List<Payment> findByMembershipId(String membershipId);

    List<Payment> findByUserId(String userId);
    List<Payment> findAll();
}
