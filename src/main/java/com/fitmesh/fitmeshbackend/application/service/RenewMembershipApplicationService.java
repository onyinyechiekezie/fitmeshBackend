package com.fitmesh.fitmeshbackend.application.service;

import com.fitmesh.fitmeshbackend.application.ports.in.RenewMembershipUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.command.RenewMembershipCommand;
import com.fitmesh.fitmeshbackend.application.ports.in.result.RenewalResult;
import com.fitmesh.fitmeshbackend.application.ports.out.*;
import com.fitmesh.fitmeshbackend.application.ports.out.result.ProviderPaymentResponse;
import com.fitmesh.fitmeshbackend.application.service.mapper.PaymentToProviderPaymentRequestMapper;
import com.fitmesh.fitmeshbackend.domain.enums.PaymentType;
import com.fitmesh.fitmeshbackend.domain.exception.GymPlanNotFoundException;
import com.fitmesh.fitmeshbackend.domain.exception.MembershipNotFoundException;
import com.fitmesh.fitmeshbackend.domain.model.GymPlan;
import com.fitmesh.fitmeshbackend.domain.model.Membership;
import com.fitmesh.fitmeshbackend.domain.model.Payment;
import com.fitmesh.fitmeshbackend.domain.valueobject.PaymentReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
public class RenewMembershipApplicationService implements RenewMembershipUseCase {

    private static final Logger logger = LoggerFactory.getLogger(
            RenewMembershipApplicationService.class
    );

    private final MembershipRepository membershipRepository;
    private final GymPlanRepository gymPlanRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentReferenceGenerator referenceGenerator;
    private final PaymentProviderPort paymentProviderPort;
    private final PaymentToProviderPaymentRequestMapper paymentMapper;

    public RenewMembershipApplicationService(
            MembershipRepository membershipRepository,
            GymPlanRepository gymPlanRepository,
            PaymentRepository paymentRepository,
            PaymentReferenceGenerator referenceGenerator,
            PaymentProviderPort paymentProviderPort,
            PaymentToProviderPaymentRequestMapper paymentMapper
    ) {
        this.membershipRepository = Objects.requireNonNull(membershipRepository);
        this.gymPlanRepository = Objects.requireNonNull(gymPlanRepository);
        this.paymentRepository = Objects.requireNonNull(paymentRepository);
        this.referenceGenerator = Objects.requireNonNull(referenceGenerator);
        this.paymentProviderPort = Objects.requireNonNull(paymentProviderPort);
        this.paymentMapper = Objects.requireNonNull(paymentMapper);
    }

    @Override
    @Transactional
    public RenewalResult renewMembership(RenewMembershipCommand command) {
        logger.info("Renewing membership: {} for user: {}",
                command.getMembershipId(),
                command.getUserId());

        // Find membership
        Membership membership = membershipRepository.findById(command.getMembershipId())
                .orElseThrow(() -> new MembershipNotFoundException(command.getMembershipId()));

        // Verify ownership
        if (!membership.getUserId().equals(command.getUserId())) {
            throw new MembershipNotFoundException(
                    "Membership does not belong to user: " + command.getUserId()
            );
        }

        // Get gym plan
        GymPlan gymPlan = gymPlanRepository.findById(membership.getPlanId())
                .orElseThrow(() -> new GymPlanNotFoundException(membership.getPlanId()));

        // Generate payment reference
        PaymentReference paymentReference = referenceGenerator.generateNext();

        // Create renewal payment
        Payment payment = new Payment(
                UUID.randomUUID().toString(),
                paymentReference,
                gymPlan.getPrice(),
                gymPlan.getCurrency(),
                PaymentType.SINGLE_PAYMENT,
                command.getUserId(),
                membership.getMembershipId(),
                "Renewal of " + gymPlan.getName(),
                null, // subscriptionFrequency - renewals are single payments
                null  // numberOfPayments - renewals are single payments
        );

        paymentRepository.save(payment);
        logger.info("Renewal payment created: {}", payment.getId());

        // Initiate payment with provider
        try {
            ProviderPaymentResponse providerResponse =
                    paymentProviderPort.initiatePayment(paymentMapper.map(payment));

            payment.attachProviderReference(providerResponse.getProviderReference());
            paymentRepository.save(payment);

            logger.info("Renewal payment initiated with provider: {}",
                    providerResponse.getProviderReference());

            return new RenewalResult(
                    membership.getMembershipId(),
                    payment.getId(),
                    payment.getPaymentReference().getValue(),
                    providerResponse.getRedirectUrl()
            );

        } catch (Exception e) {
            logger.error("Failed to initiate renewal payment with provider", e);

            payment.markFailed();
            paymentRepository.save(payment);

            throw new RuntimeException(
                    "Failed to initiate renewal payment: " + e.getMessage(),
                    e
            );
        }
    }
}
