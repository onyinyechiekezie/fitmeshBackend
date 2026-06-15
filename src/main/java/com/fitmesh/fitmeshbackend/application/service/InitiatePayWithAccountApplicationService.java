package com.fitmesh.fitmeshbackend.application.service;

import com.fitmesh.fitmeshbackend.application.ports.in.InitiatePayWithAccountUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.command.InitiatePayWithAccountCommand;
import com.fitmesh.fitmeshbackend.application.ports.in.result.InitiatePaymentResult;
import com.fitmesh.fitmeshbackend.application.ports.out.PaymentProviderPort;
import com.fitmesh.fitmeshbackend.application.ports.out.PaymentReferenceGenerator;
import com.fitmesh.fitmeshbackend.application.ports.out.PaymentRepository;
import com.fitmesh.fitmeshbackend.application.ports.out.UserRepository;
import com.fitmesh.fitmeshbackend.application.ports.out.result.ProviderPaymentResponse;
import com.fitmesh.fitmeshbackend.application.service.mapper.PaymentToProviderPaymentRequestMapper;
import com.fitmesh.fitmeshbackend.domain.exception.UserNotFoundException;
import com.fitmesh.fitmeshbackend.domain.model.Payment;
import com.fitmesh.fitmeshbackend.domain.model.User;
import com.fitmesh.fitmeshbackend.domain.valueobject.PaymentReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Service
public class InitiatePayWithAccountApplicationService
        implements InitiatePayWithAccountUseCase {

    private static final Logger logger = LoggerFactory.getLogger(InitiatePayWithAccountApplicationService.class);

    private final PaymentProviderPort paymentProviderPort;
    private final PaymentToProviderPaymentRequestMapper mapper;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final PaymentReferenceGenerator referenceGenerator;

    public InitiatePayWithAccountApplicationService(
            PaymentProviderPort paymentProviderPort,
            PaymentToProviderPaymentRequestMapper mapper,
            PaymentRepository paymentRepository,
            UserRepository userRepository,
            PaymentReferenceGenerator referenceGenerator
    ) {
        this.paymentProviderPort = Objects.requireNonNull(
                paymentProviderPort,
                "paymentProviderPort must not be null"
        );
        this.mapper = Objects.requireNonNull(
                mapper,
                "mapper must not be null"
        );
        this.paymentRepository = Objects.requireNonNull(
                paymentRepository,
                "paymentRepository must not be null"
        );
        this.userRepository = Objects.requireNonNull(
                userRepository,
                "userRepository must not be null"
        );
        this.referenceGenerator = Objects.requireNonNull(
                referenceGenerator,
                "referenceGenerator must not be null"
        );
    }

    @Override
    public InitiatePaymentResult initiate(InitiatePayWithAccountCommand command) {
        Objects.requireNonNull(command, "command must not be null");

        logger.info("Initiating payment for user: {}", command.getUserId());

        // STEP 1: Validate user exists and can initiate payment
        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new UserNotFoundException(command.getUserId()));

        user.validateCanInitiatePayment();
        logger.debug("User validation passed for: {}", command.getUserId());

        // STEP 2: Generate payment reference
        PaymentReference paymentReference = referenceGenerator.generateNext();
        logger.debug("Generated payment reference: {}", paymentReference.getValue());

        // STEP 3: Create payment domain object
        Payment payment = new Payment(
                UUID.randomUUID().toString(),
                paymentReference,
                command.getAmount(),
                command.getCurrency(),
                command.getPaymentType(),
                command.getUserId(),
                null, // membershipId - will be set when creating membership payments
                command.getDescription(),
                null, // subscriptionFrequency - not applicable for direct payments
                null  // numberOfPayments - not applicable for direct payments
        );

        // STEP 4: Save payment in PENDING state
        paymentRepository.save(payment);
        logger.info("Payment saved with ID: {} and reference: {}",
                payment.getId(), payment.getPaymentReference().getValue());

        try {
            // STEP 5: Initiate payment with provider
            ProviderPaymentResponse providerResponse =
                    paymentProviderPort.initiatePayment(
                            mapper.map(payment)
                    );

            // STEP 6: Attach provider reference and save again
            payment.attachProviderReference(
                    providerResponse.getProviderReference()
            );

            paymentRepository.save(payment);

            logger.info("Payment initiated successfully with provider reference: {}",
                    providerResponse.getProviderReference());

            // STEP 7: Return result
            return new InitiatePaymentResult(
                    payment.getId(),
                    payment.getPaymentReference().getValue(),
                    providerResponse.getStatus(),
                    providerResponse.getRedirectUrl(),
                    Instant.now()
            );

        } catch (Exception e) {
            logger.error("Failed to initiate payment with provider", e);

            // Mark payment as failed
            payment.markFailed();
            paymentRepository.save(payment);

            throw new RuntimeException("Failed to initiate payment with provider: " + e.getMessage(), e);
        }
    }
}