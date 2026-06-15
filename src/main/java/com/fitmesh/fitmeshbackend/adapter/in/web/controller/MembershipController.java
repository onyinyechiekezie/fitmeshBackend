package com.fitmesh.fitmeshbackend.adapter.in.web.controller;

import com.fitmesh.fitmeshbackend.adapter.in.web.dto.request.RenewRequest;
import com.fitmesh.fitmeshbackend.adapter.in.web.dto.request.SubscribeRequest;
import com.fitmesh.fitmeshbackend.adapter.in.web.dto.response.MembershipResponse;
import com.fitmesh.fitmeshbackend.adapter.in.web.dto.response.RenewalResponse;
import com.fitmesh.fitmeshbackend.adapter.in.web.dto.response.SubscriptionResponse;
import com.fitmesh.fitmeshbackend.application.ports.in.*;
import com.fitmesh.fitmeshbackend.application.ports.in.command.*;
import com.fitmesh.fitmeshbackend.application.ports.in.result.MembershipResult;
import com.fitmesh.fitmeshbackend.application.ports.in.result.RenewalResult;
import com.fitmesh.fitmeshbackend.application.ports.in.result.SubscriptionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/memberships")
public class MembershipController {

    private final SubscribeToGymPlanUseCase subscribeToGymPlanUseCase;
    private final GetCurrentMembershipUseCase getCurrentMembershipUseCase;
    private final CancelMembershipUseCase cancelMembershipUseCase;
    private final PauseMembershipUseCase pauseMembershipUseCase;
    private final ResumeMembershipUseCase resumeMembershipUseCase;
    private final RenewMembershipUseCase renewMembershipUseCase;
    private final AuthenticatedUserProvider authProvider;

    public MembershipController(
            SubscribeToGymPlanUseCase subscribeToGymPlanUseCase,
            GetCurrentMembershipUseCase getCurrentMembershipUseCase,
            CancelMembershipUseCase cancelMembershipUseCase,
            PauseMembershipUseCase pauseMembershipUseCase,
            ResumeMembershipUseCase resumeMembershipUseCase,
            RenewMembershipUseCase renewMembershipUseCase,
            AuthenticatedUserProvider authProvider
    ) {
        this.subscribeToGymPlanUseCase = subscribeToGymPlanUseCase;
        this.getCurrentMembershipUseCase = getCurrentMembershipUseCase;
        this.cancelMembershipUseCase = cancelMembershipUseCase;
        this.pauseMembershipUseCase = pauseMembershipUseCase;
        this.resumeMembershipUseCase = resumeMembershipUseCase;
        this.renewMembershipUseCase = renewMembershipUseCase;
        this.authProvider = authProvider;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<SubscriptionResponse> subscribe(
            @RequestBody SubscribeRequest request
    ) {
        String userId = authProvider.getCurrentUserId();

        SubscribeToGymPlanCommand command = new SubscribeToGymPlanCommand(
                userId,
                request.getPlanId()
        );

        SubscriptionResult result = subscribeToGymPlanUseCase.subscribe(command);

        SubscriptionResponse response = new SubscriptionResponse(
                result.getMembershipId(),
                result.getPaymentId(),
                result.getPaymentReference(),
                result.getMembershipStatus(),
                result.getRedirectUrl()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<MembershipResponse> getCurrentMembership() {
        String userId = authProvider.getCurrentUserId();

        return getCurrentMembershipUseCase.getCurrentMembership(userId)
                .map(result -> {
                    MembershipResponse response = new MembershipResponse(
                            result.getMembershipId(),
                            result.getUserId(),
                            result.getPlanId(),
                            result.getPlanName(),
                            result.getStatus(),
                            result.getStartDate(),
                            result.getEndDate(),
                            result.getLastPaymentDate(),
                            result.isActive(),
                            result.isExpired()
                    );
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/cancel")
    public ResponseEntity<Void> cancelMembership() {
        String userId = authProvider.getCurrentUserId();

        CancelMembershipCommand command = new CancelMembershipCommand(userId);
        cancelMembershipUseCase.cancelMembership(command);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/pause")
    public ResponseEntity<Void> pauseMembership() {
        String userId = authProvider.getCurrentUserId();

        PauseMembershipCommand command = new PauseMembershipCommand(userId);
        pauseMembershipUseCase.pauseMembership(command);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/resume")
    public ResponseEntity<Void> resumeMembership() {
        String userId = authProvider.getCurrentUserId();

        ResumeMembershipCommand command = new ResumeMembershipCommand(userId);
        resumeMembershipUseCase.resumeMembership(command);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/renew")
    public ResponseEntity<RenewalResponse> renewMembership(
            @RequestBody RenewRequest request
    ) {
        String userId = authProvider.getCurrentUserId();

        RenewMembershipCommand command = new RenewMembershipCommand(
                userId,
                request.getMembershipId()
        );

        RenewalResult result = renewMembershipUseCase.renewMembership(command);

        RenewalResponse response = new RenewalResponse(
                result.getMembershipId(),
                result.getPaymentId(),
                result.getPaymentReference(),
                result.getRedirectUrl()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
