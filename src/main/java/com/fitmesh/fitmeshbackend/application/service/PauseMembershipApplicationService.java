package com.fitmesh.fitmeshbackend.application.service;

import com.fitmesh.fitmeshbackend.application.ports.in.PauseMembershipUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.command.PauseMembershipCommand;
import com.fitmesh.fitmeshbackend.application.ports.out.MembershipRepository;
import com.fitmesh.fitmeshbackend.domain.exception.MembershipNotFoundException;
import com.fitmesh.fitmeshbackend.domain.model.Membership;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PauseMembershipApplicationService implements PauseMembershipUseCase {

    private static final Logger logger = LoggerFactory.getLogger(
            PauseMembershipApplicationService.class
    );

    private final MembershipRepository membershipRepository;

    public PauseMembershipApplicationService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    @Transactional
    public void pauseMembership(PauseMembershipCommand command) {
        logger.info("Pausing membership for user: {}", command.getUserId());

        // Find active membership
        Membership membership = membershipRepository.findActiveByUserId(command.getUserId())
                .orElseThrow(() -> {
                    logger.error("No active membership found for user: {}",
                            command.getUserId());
                    return new MembershipNotFoundException(
                            "No active membership found for user: " + command.getUserId()
                    );
                });

        // Pause membership
        membership.pause();
        membershipRepository.save(membership);

        logger.info("Membership paused: {}", membership.getMembershipId());
    }
}
