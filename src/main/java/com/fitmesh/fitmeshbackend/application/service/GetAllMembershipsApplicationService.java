package com.fitmesh.fitmeshbackend.application.service;

import com.fitmesh.fitmeshbackend.application.ports.in.GetAllMembershipsUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.result.AdminMembershipResult;
import com.fitmesh.fitmeshbackend.application.ports.out.GymPlanRepository;
import com.fitmesh.fitmeshbackend.application.ports.out.MembershipRepository;
import com.fitmesh.fitmeshbackend.application.ports.out.UserRepository;
import com.fitmesh.fitmeshbackend.domain.exception.GymPlanNotFoundException;
import com.fitmesh.fitmeshbackend.domain.exception.UserNotFoundException;
import com.fitmesh.fitmeshbackend.domain.model.GymPlan;
import com.fitmesh.fitmeshbackend.domain.model.Membership;
import com.fitmesh.fitmeshbackend.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllMembershipsApplicationService implements GetAllMembershipsUseCase {

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final GymPlanRepository gymPlanRepository;

    public GetAllMembershipsApplicationService(
            MembershipRepository membershipRepository,
            UserRepository userRepository,
            GymPlanRepository gymPlanRepository
    ) {
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
        this.gymPlanRepository = gymPlanRepository;
    }

    @Override
    public List<AdminMembershipResult> getAllMemberships() {
        return membershipRepository.findAll().stream()
                .map(this::toResult)
                .collect(Collectors.toList());
    }

    private AdminMembershipResult toResult(Membership membership) {
        // Get user details
        User user = userRepository.findById(membership.getUserId())
                .orElseThrow(() -> new UserNotFoundException(membership.getUserId()));

        // Get plan details
        GymPlan gymPlan = gymPlanRepository.findById(membership.getPlanId())
                .orElseThrow(() -> new GymPlanNotFoundException(membership.getPlanId()));

        return new AdminMembershipResult(
                membership.getMembershipId(),
                membership.getUserId(),
                user.getEmail(),
                membership.getPlanId(),
                gymPlan.getName(),
                membership.getStatus(),
                membership.getStartDate(),
                membership.getEndDate(),
                membership.getLastPaymentDate(),
                membership.getCreatedAt()
        );
    }
}
