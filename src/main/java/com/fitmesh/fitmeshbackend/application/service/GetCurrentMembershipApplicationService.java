package com.fitmesh.fitmeshbackend.application.service;

import com.fitmesh.fitmeshbackend.application.ports.in.GetCurrentMembershipUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.result.MembershipResult;
import com.fitmesh.fitmeshbackend.application.ports.out.GymPlanRepository;
import com.fitmesh.fitmeshbackend.application.ports.out.MembershipRepository;
import com.fitmesh.fitmeshbackend.domain.exception.GymPlanNotFoundException;
import com.fitmesh.fitmeshbackend.domain.model.GymPlan;
import com.fitmesh.fitmeshbackend.domain.model.Membership;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetCurrentMembershipApplicationService implements GetCurrentMembershipUseCase {

    private final MembershipRepository membershipRepository;
    private final GymPlanRepository gymPlanRepository;

    public GetCurrentMembershipApplicationService(
            MembershipRepository membershipRepository,
            GymPlanRepository gymPlanRepository
    ) {
        this.membershipRepository = membershipRepository;
        this.gymPlanRepository = gymPlanRepository;
    }

    @Override
    public Optional<MembershipResult> getCurrentMembership(String userId) {
        return membershipRepository.findActiveByUserId(userId)
                .map(membership -> {
                    // Get plan details
                    GymPlan gymPlan = gymPlanRepository.findById(membership.getPlanId())
                            .orElseThrow(() -> new GymPlanNotFoundException(
                                    membership.getPlanId()
                            ));

                    return new MembershipResult(
                            membership.getMembershipId(),
                            membership.getUserId(),
                            membership.getPlanId(),
                            gymPlan.getName(),
                            membership.getStatus(),
                            membership.getStartDate(),
                            membership.getEndDate(),
                            membership.getLastPaymentDate(),
                            membership.isActive(),
                            membership.isExpired()
                    );
                });
    }
}
