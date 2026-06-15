package com.fitmesh.fitmeshbackend.application.service;

import com.fitmesh.fitmeshbackend.application.ports.in.GetAllPaymentsUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.result.AdminPaymentResult;
import com.fitmesh.fitmeshbackend.application.ports.out.PaymentRepository;
import com.fitmesh.fitmeshbackend.application.ports.out.UserRepository;
import com.fitmesh.fitmeshbackend.domain.exception.UserNotFoundException;
import com.fitmesh.fitmeshbackend.domain.model.Payment;
import com.fitmesh.fitmeshbackend.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllPaymentsApplicationService implements GetAllPaymentsUseCase {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public GetAllPaymentsApplicationService(
            PaymentRepository paymentRepository,
            UserRepository userRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<AdminPaymentResult> getAllPayments() {
        return paymentRepository.findAll().stream()
                .sorted(Comparator.comparing(Payment::getCreatedAt).reversed())
                .map(this::toResult)
                .collect(Collectors.toList());
    }

    private AdminPaymentResult toResult(Payment payment) {
        // Get user details
        User user = userRepository.findById(payment.getUserId())
                .orElseThrow(() -> new UserNotFoundException(payment.getUserId()));

        return new AdminPaymentResult(
                payment.getId(),
                payment.getPaymentReference().getValue(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getPaymentType(),
                payment.getPaymentStatus(),
                payment.getUserId(),
                user.getEmail(),
                payment.getMembershipId(),
                payment.getDescription(),
                payment.getProviderReference(),
                payment.getCreatedAt()
        );
    }
}
