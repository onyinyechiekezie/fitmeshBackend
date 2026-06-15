package com.fitmesh.fitmeshbackend.application.service.mapper;

import com.fitmesh.fitmeshbackend.application.ports.out.UserRepository;
import com.fitmesh.fitmeshbackend.application.ports.out.command.ProviderPaymentRequest;
import com.fitmesh.fitmeshbackend.domain.exception.UserNotFoundException;
import com.fitmesh.fitmeshbackend.domain.model.Payment;
import com.fitmesh.fitmeshbackend.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class PaymentToProviderPaymentRequestMapper {

    private final UserRepository userRepository;

    public PaymentToProviderPaymentRequestMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ProviderPaymentRequest map(Payment payment) {
        // Fetch user to get email and phone
        User user = userRepository.findById(payment.getUserId())
                .orElseThrow(() -> new UserNotFoundException(payment.getUserId()));

        return new ProviderPaymentRequest(
                payment.getPaymentReference().getValue(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getPaymentType(),
                payment.getUserId(),
                user.getEmail(),
                user.getPhoneNumber(),
                payment.getDescription(),
                payment.getSubscriptionFrequency(),
                payment.getNumberOfPayments()
        );
    }
}