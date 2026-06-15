package com.fitmesh.fitmeshbackend.application.service;

import com.fitmesh.fitmeshbackend.application.ports.in.SubscribeToGymPlanUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.command.SubscribeToGymPlanCommand;
import com.fitmesh.fitmeshbackend.application.ports.in.result.SubscriptionResult;
import com.fitmesh.fitmeshbackend.application.ports.out.*;
import com.fitmesh.fitmeshbackend.application.ports.out.result.ProviderPaymentResponse;
import com.fitmesh.fitmeshbackend.application.service.mapper.PaymentToProviderPaymentRequestMapper;
import com.fitmesh.fitmeshbackend.domain.enums.BillingFrequency;
import com.fitmesh.fitmeshbackend.domain.enums.PaymentType;
import com.fitmesh.fitmeshbackend.domain.exception.*;
import com.fitmesh.fitmeshbackend.domain.model.GymPlan;
import com.fitmesh.fitmeshbackend.domain.model.Membership;
import com.fitmesh.fitmeshbackend.domain.model.Payment;
import com.fitmesh.fitmeshbackend.domain.model.User;
import com.fitmesh.fitmeshbackend.domain.service.BillingFrequencyMapper;
import com.fitmesh.fitmeshbackend.domain.valueobject.PaymentReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
public class SubscribeToGymPlanApplicationService implements SubscribeToGymPlanUseCase {

    private static final Logger logger = LoggerFactory.getLogger(
            SubscribeToGymPlanApplicationService.class
    );

    private final GymPlanRepository gymPlanRepository;
    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentReferenceGenerator referenceGenerator;
    private final PaymentProviderPort paymentProviderPort;
    private final PaymentToProviderPaymentRequestMapper paymentMapper;

    public SubscribeToGymPlanApplicationService(
            GymPlanRepository gymPlanRepository,
            MembershipRepository membershipRepository,
            UserRepository userRepository,
            PaymentRepository paymentRepository,
            PaymentReferenceGenerator referenceGenerator,
            PaymentProviderPort paymentProviderPort,
            PaymentToProviderPaymentRequestMapper paymentMapper
    ) {
        this.gymPlanRepository = Objects.requireNonNull(gymPlanRepository);
        this.membershipRepository = Objects.requireNonNull(membershipRepository);
        this.userRepository = Objects.requireNonNull(userRepository);
        this.paymentRepository = Objects.requireNonNull(paymentRepository);
        this.referenceGenerator = Objects.requireNonNull(referenceGenerator);
        this.paymentProviderPort = Objects.requireNonNull(paymentProviderPort);
        this.paymentMapper = Objects.requireNonNull(paymentMapper);
    }

    @Override
    @Transactional
    public SubscriptionResult subscribe(SubscribeToGymPlanCommand command) {
        logger.info("User {} subscribing to plan {}", command.getUserId(), command.getPlanId());

        // STEP 1: Validate user exists and can initiate payment
        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new UserNotFoundException(command.getUserId()));

        user.validateCanInitiatePayment();

        // STEP 2: Check if user already has an active membership
        membershipRepository.findActiveByUserId(command.getUserId())
                .ifPresent(membership -> {
                    throw new UserAlreadyHasMembershipException(command.getUserId());
                });

        // STEP 3: Get gym plan and validate it's active
        GymPlan gymPlan = gymPlanRepository.findById(command.getPlanId())
                .orElseThrow(() -> new GymPlanNotFoundException(command.getPlanId()));

        if (!gymPlan.isActive()) {
            throw new InactivePlanException(command.getPlanId());
        }

        // STEP 4: Create membership (PENDING status)
        Membership membership = new Membership(
                UUID.randomUUID().toString(),
                command.getUserId(),
                command.getPlanId()
        );

        membershipRepository.save(membership);
        logger.info("Membership created: {}", membership.getMembershipId());

        // STEP 5: Generate payment reference
        PaymentReference paymentReference = referenceGenerator.generateNext();

        // STEP 6: Create payment (linked to membership)
        // Determine payment type and subscription details based on plan
        PaymentType paymentType;
        String subscriptionFrequency = null;
        Integer numberOfPayments = null;

        // For now,QUARTERLY and YEARLY plans use subscriptions
        // MONTHLY can be either single or subscription based on user preference
        if (gymPlan.getBillingFrequency() == BillingFrequency.QUARTERLY ||
                gymPlan.getBillingFrequency() == BillingFrequency.YEARLY) {
            paymentType = PaymentType.SUBSCRIPTION;
            subscriptionFrequency = BillingFrequencyMapper.toOnePipeFrequency(
                    gymPlan.getBillingFrequency()
            );
            numberOfPayments = BillingFrequencyMapper.getNumberOfPayments(
                    gymPlan.getBillingFrequency()
            );
        } else {
            // MONTHLY defaults to single payment (user can renew manually each month)
            paymentType = PaymentType.SINGLE_PAYMENT;
        }

        Payment payment = new Payment(
                UUID.randomUUID().toString(),
                paymentReference,
                gymPlan.getPrice(),
                gymPlan.getCurrency(),
                paymentType,
                command.getUserId(),
                membership.getMembershipId(),
                "Subscription to " + gymPlan.getName(),
                subscriptionFrequency,
                numberOfPayments
        );

        paymentRepository.save(payment);
        logger.info("Payment created: {} for membership: {}",
                payment.getId(),
                membership.getMembershipId());

        // STEP 7: Initiate payment with provider
        try {
            ProviderPaymentResponse providerResponse =
                    paymentProviderPort.initiatePayment(paymentMapper.map(payment));

            // Attach provider reference
            payment.attachProviderReference(providerResponse.getProviderReference());
            paymentRepository.save(payment);

            logger.info("Payment initiated with provider: {}",
                    providerResponse.getProviderReference());

            // STEP 8: Return result
            return new SubscriptionResult(
                    membership.getMembershipId(),
                    payment.getId(),
                    payment.getPaymentReference().getValue(),
                    membership.getStatus(),
                    providerResponse.getRedirectUrl()
            );

        } catch (Exception e) {
            logger.error("Failed to initiate payment with provider", e);

            // Mark payment as failed
            payment.markFailed();
            paymentRepository.save(payment);

            throw new RuntimeException(
                    "Failed to initiate payment with provider: " + e.getMessage(),
                    e
            );
        }
    }
}
