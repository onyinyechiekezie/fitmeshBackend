package com.fitmesh.fitmeshbackend.application.service;

import com.fitmesh.fitmeshbackend.application.ports.in.CancelMembershipUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.command.CancelMembershipCommand;
import com.fitmesh.fitmeshbackend.application.ports.out.MembershipRepository;
import com.fitmesh.fitmeshbackend.domain.exception.MembershipNotFoundException;
import com.fitmesh.fitmeshbackend.domain.model.Membership;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CancelMembershipApplicationService implements CancelMembershipUseCase {

    private static final Logger logger = LoggerFactory.getLogger(
            CancelMembershipApplicationService.class
    );

    private final MembershipRepository membershipRepository;

    public CancelMembershipApplicationService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    @Transactional
    public void cancelMembership(CancelMembershipCommand command) {
        logger.info("Cancelling membership for user: {}", command.getUserId());

        // Find active membership
        Membership membership = membershipRepository.findActiveByUserId(command.getUserId())
                .orElseThrow(() -> {
                    logger.error("No active membership found for user: {}",
                            command.getUserId());
                    return new MembershipNotFoundException(
                            "No active membership found for user: " + command.getUserId()
                    );
                });

        // Cancel membership
        membership.cancel();
        membershipRepository.save(membership);

        logger.info("Membership cancelled: {}", membership.getMembershipId());
    }
}
