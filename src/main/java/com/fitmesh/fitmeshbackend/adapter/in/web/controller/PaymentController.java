package com.fitmesh.fitmeshbackend.adapter.in.web.controller;

import com.fitmesh.fitmeshbackend.adapter.in.web.dto.request.InitiatePayWithAccountRequest;
import com.fitmesh.fitmeshbackend.adapter.in.web.dto.response.InitiatePaymentResponse;
import com.fitmesh.fitmeshbackend.adapter.in.web.mapper.InitiatePayWithAccountWebMapper;
import com.fitmesh.fitmeshbackend.application.ports.in.AuthenticatedUserProvider;
import com.fitmesh.fitmeshbackend.application.ports.in.GetPaymentByReferenceUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.GetPaymentHistoryUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.result.PaymentHistoryResult;
import com.fitmesh.fitmeshbackend.application.ports.in.result.PaymentResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final InitiatePayWithAccountWebMapper mapper;
    private final GetPaymentByReferenceUseCase getPaymentByReferenceUseCase;
    private final GetPaymentHistoryUseCase getPaymentHistoryUseCase;
    private final AuthenticatedUserProvider authProvider;

    public PaymentController(
            InitiatePayWithAccountWebMapper mapper,
            GetPaymentByReferenceUseCase getPaymentByReferenceUseCase,
            GetPaymentHistoryUseCase getPaymentHistoryUseCase,
            AuthenticatedUserProvider authProvider
    ) {
        this.mapper = mapper;
        this.getPaymentByReferenceUseCase = getPaymentByReferenceUseCase;
        this.getPaymentHistoryUseCase = getPaymentHistoryUseCase;
        this.authProvider = authProvider;
    }

    @PostMapping("/pay-with-account")
    public ResponseEntity<InitiatePaymentResponse> initiatePayWithAccount(
            @RequestBody InitiatePayWithAccountRequest request
    ) {
        return ResponseEntity.ok(
                mapper.initiatePayWithAccount(request)
        );
    }

    @GetMapping("/reference/{reference}")
    public ResponseEntity<PaymentResult> getPaymentByReference(
            @PathVariable String reference
    ) {
        PaymentResult result = getPaymentByReferenceUseCase.getByReference(reference);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/history")
    public ResponseEntity<List<PaymentHistoryResult>> getMyPaymentHistory() {
        String userId = authProvider.getCurrentUserId();
        List<PaymentHistoryResult> history =
                getPaymentHistoryUseCase.getPaymentHistoryForUser(userId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/history/membership/{membershipId}")
    public ResponseEntity<List<PaymentHistoryResult>> getPaymentHistoryForMembership(
            @PathVariable String membershipId
    ) {
        String userId = authProvider.getCurrentUserId();
        List<PaymentHistoryResult> history =
                getPaymentHistoryUseCase.getPaymentHistoryForMembership(userId, membershipId);
        return ResponseEntity.ok(history);
    }
}