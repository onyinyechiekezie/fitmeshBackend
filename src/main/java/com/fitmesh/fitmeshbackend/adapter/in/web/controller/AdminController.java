package com.fitmesh.fitmeshbackend.adapter.in.web.controller;

import com.fitmesh.fitmeshbackend.application.ports.in.GetAllMembershipsUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.GetAllPaymentsUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.GetAllUsersUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.result.AdminMembershipResult;
import com.fitmesh.fitmeshbackend.application.ports.in.result.AdminPaymentResult;
import com.fitmesh.fitmeshbackend.application.ports.in.result.AdminUserResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetAllMembershipsUseCase getAllMembershipsUseCase;
    private final GetAllPaymentsUseCase getAllPaymentsUseCase;

    public AdminController(
            GetAllUsersUseCase getAllUsersUseCase,
            GetAllMembershipsUseCase getAllMembershipsUseCase,
            GetAllPaymentsUseCase getAllPaymentsUseCase
    ) {
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.getAllMembershipsUseCase = getAllMembershipsUseCase;
        this.getAllPaymentsUseCase = getAllPaymentsUseCase;
    }

    @GetMapping("/users")
    public ResponseEntity<List<AdminUserResult>> getAllUsers() {
        List<AdminUserResult> users = getAllUsersUseCase.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/memberships")
    public ResponseEntity<List<AdminMembershipResult>> getAllMemberships() {
        List<AdminMembershipResult> memberships = getAllMembershipsUseCase.getAllMemberships();
        return ResponseEntity.ok(memberships);
    }

    @GetMapping("/payments")
    public ResponseEntity<List<AdminPaymentResult>> getAllPayments() {
        List<AdminPaymentResult> payments = getAllPaymentsUseCase.getAllPayments();
        return ResponseEntity.ok(payments);
    }
}
