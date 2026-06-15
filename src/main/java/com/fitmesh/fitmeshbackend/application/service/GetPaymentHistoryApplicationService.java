package com.fitmesh.fitmeshbackend.application.service;

import com.fitmesh.fitmeshbackend.application.ports.in.GetPaymentHistoryUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.result.PaymentHistoryResult;
import com.fitmesh.fitmeshbackend.application.ports.out.MembershipRepository;
import com.fitmesh.fitmeshbackend.application.ports.out.PaymentRepository;
import com.fitmesh.fitmeshbackend.domain.exception.MembershipNotFoundException;
import com.fitmesh.fitmeshbackend.domain.model.Membership;
import com.fitmesh.fitmeshbackend.domain.model.Payment;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetPaymentHistoryApplicationService implements GetPaymentHistoryUseCase {

    private final PaymentRepository paymentRepository;
    private final MembershipRepository membershipRepository;

    public GetPaymentHistoryApplicationService(
            PaymentRepository paymentRepository,
            MembershipRepository membershipRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.membershipRepository = membershipRepository;
    }

    @Override
    public List<PaymentHistoryResult> getPaymentHistoryForUser(String userId) {
        return paymentRepository.findByUserId(userId).stream()
                .sorted(Comparator.comparing(Payment::getCreatedAt).reversed())
                .map(this::toResult)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentHistoryResult> getPaymentHistoryForMembership(
            String userId,
            String membershipId
    ) {
        // Verify membership belongs to user
        Membership membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new MembershipNotFoundException(membershipId));

        if (!membership.getUserId().equals(userId)) {
            throw new MembershipNotFoundException(
                    "Membership does not belong to user: " + userId
            );
        }

        return paymentRepository.findByMembershipId(membershipId).stream()
                .sorted(Comparator.comparing(Payment::getCreatedAt).reversed())
                .map(this::toResult)
                .collect(Collectors.toList());
    }

    private PaymentHistoryResult toResult(Payment payment) {
        return new PaymentHistoryResult(
                payment.getId(),
                payment.getPaymentReference().getValue(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getPaymentType(),
                payment.getPaymentStatus(),
                payment.getDescription(),
                payment.getProviderReference(),
                payment.getCreatedAt()
        );
    }
}
