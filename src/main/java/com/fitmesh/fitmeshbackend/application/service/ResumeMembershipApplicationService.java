package com.fitmesh.fitmeshbackend.application.service;

import com.fitmesh.fitmeshbackend.application.ports.in.ResumeMembershipUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.command.ResumeMembershipCommand;
import com.fitmesh.fitmeshbackend.application.ports.out.MembershipRepository;
import com.fitmesh.fitmeshbackend.domain.enums.MembershipStatus;
import com.fitmesh.fitmeshbackend.domain.exception.MembershipNotFoundException;
import com.fitmesh.fitmeshbackend.domain.model.Membership;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResumeMembershipApplicationService implements ResumeMembershipUseCase {

    private static final Logger logger = LoggerFactory.getLogger(
            ResumeMembershipApplicationService.class
    );

    private final MembershipRepository membershipRepository;

    public ResumeMembershipApplicationService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    @Transactional
    public void resumeMembership(ResumeMembershipCommand command) {
        logger.info("Resuming membership for user: {}", command.getUserId());

        // Find paused membership
        List<Membership> memberships = membershipRepository.findByUserId(command.getUserId());

        Membership pausedMembership = memberships.stream()
                .filter(m -> m.getStatus() == MembershipStatus.PAUSED)
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("No paused membership found for user: {}",
                            command.getUserId());
                    return new MembershipNotFoundException(
                            "No paused membership found for user: " + command.getUserId()
                    );
                });

        // Resume membership
        pausedMembership.resume();
        membershipRepository.save(pausedMembership);

        logger.info("Membership resumed: {}", pausedMembership.getMembershipId());
    }
}
