package com.fitmesh.fitmeshbackend.application.service;

import com.fitmesh.fitmeshbackend.application.ports.in.ProcessPaymentWebhookUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.command.ProcessPaymentWebhookCommand;
import com.fitmesh.fitmeshbackend.application.ports.out.GymPlanRepository;
import com.fitmesh.fitmeshbackend.application.ports.out.MembershipRepository;
import com.fitmesh.fitmeshbackend.application.ports.out.PaymentRepository;
import com.fitmesh.fitmeshbackend.domain.enums.MembershipStatus;
import com.fitmesh.fitmeshbackend.domain.exception.GymPlanNotFoundException;
import com.fitmesh.fitmeshbackend.domain.exception.InvalidPaymentStateException;
import com.fitmesh.fitmeshbackend.domain.exception.MembershipNotFoundException;
import com.fitmesh.fitmeshbackend.domain.exception.PaymentNotFoundException;
import com.fitmesh.fitmeshbackend.domain.model.GymPlan;
import com.fitmesh.fitmeshbackend.domain.model.Membership;
import com.fitmesh.fitmeshbackend.domain.model.Payment;
import com.fitmesh.fitmeshbackend.domain.valueobject.PaymentReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class ProcessPaymentWebhookApplicationService
        implements ProcessPaymentWebhookUseCase {

    private static final Logger logger = LoggerFactory.getLogger(
            ProcessPaymentWebhookApplicationService.class
    );

    private final PaymentRepository paymentRepository;
    private final MembershipRepository membershipRepository;
    private final GymPlanRepository gymPlanRepository;

    public ProcessPaymentWebhookApplicationService(
            PaymentRepository paymentRepository,
            MembershipRepository membershipRepository,
            GymPlanRepository gymPlanRepository
    ) {
        this.paymentRepository = Objects.requireNonNull(
                paymentRepository,
                "paymentRepository must not be null"
        );
        this.membershipRepository = Objects.requireNonNull(
                membershipRepository,
                "membershipRepository must not be null"
        );
        this.gymPlanRepository = Objects.requireNonNull(
                gymPlanRepository,
                "gymPlanRepository must not be null"
        );
    }

    @Override
    @Transactional
    public void processWebhook(ProcessPaymentWebhookCommand command) {
        logger.info("Processing webhook for transaction: {}",
                command.getTransactionReference());

        // Find payment by reference
        PaymentReference reference = PaymentReference.of(
                command.getTransactionReference()
        );

        Payment payment = paymentRepository.findByReference(reference)
                .orElseThrow(() -> {
                    logger.error("Payment not found for reference: {}",
                            command.getTransactionReference());
                    return new PaymentNotFoundException(
                            command.getTransactionReference()
                    );
                });

        // Log current state
        logger.info("Payment found - Current status: {}, Membership ID: {}",
                payment.getPaymentStatus(),
                payment.getMembershipId());

        // Update payment status based on webhook status
        String webhookStatus = command.getStatus().toUpperCase();

        try {
            switch (webhookStatus) {
                case "SUCCESS":
                case "SUCCESSFUL":
                case "COMPLETED":
                    handleSuccessfulPayment(payment);
                    break;

                case "FAILED":
                case "DECLINED":
                case "CANCELLED":
                    handleFailedPayment(payment);
                    break;

                default:
                    logger.warn("Unknown webhook status: {} for payment: {}",
                            webhookStatus,
                            command.getTransactionReference());
                    // Don't update status for unknown statuses
                    return;
            }

        } catch (InvalidPaymentStateException e) {
            logger.error("Failed to update payment status: {}", e.getMessage());
            throw e;
        }
    }

    private void handleSuccessfulPayment(Payment payment) {
        // Mark payment as successful
        payment.markSuccessful();
        paymentRepository.save(payment);

        logger.info("Payment marked as SUCCESSFUL: {}",
                payment.getPaymentReference().getValue());

        // If payment is linked to a membership, activate it
        if (payment.getMembershipId() != null) {
            activateMembership(payment.getMembershipId());
        }
    }

    private void handleFailedPayment(Payment payment) {
        payment.markFailed();
        paymentRepository.save(payment);

        logger.info("Payment marked as FAILED: {}",
                payment.getPaymentReference().getValue());

        // No need to do anything with membership - it stays PENDING
    }

    private void activateMembership(String membershipId) {
        logger.info("Processing membership activation/renewal: {}", membershipId);

        // Find membership
        Membership membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> {
                    logger.error("Membership not found: {}", membershipId);
                    return new MembershipNotFoundException(membershipId);
                });

        // Get gym plan to determine duration
        GymPlan gymPlan = gymPlanRepository.findById(membership.getPlanId())
                .orElseThrow(() -> {
                    logger.error("Gym plan not found: {}", membership.getPlanId());
                    return new GymPlanNotFoundException(membership.getPlanId());
                });

        // Check if this is first activation or renewal
        if (membership.getStatus() == MembershipStatus.PENDING) {
            // First activation
            membership.activate(gymPlan.getDurationInDays());
            logger.info("Membership activated: {} - Valid until: {}",
                    membershipId,
                    membership.getEndDate());
        } else if (membership.getStatus() == MembershipStatus.ACTIVE ||
                membership.getStatus() == MembershipStatus.EXPIRED) {
            // Renewal
            membership.renew(gymPlan.getDurationInDays());
            logger.info("Membership renewed: {} - New end date: {}",
                    membershipId,
                    membership.getEndDate());
        }

        membershipRepository.save(membership);
    }
}