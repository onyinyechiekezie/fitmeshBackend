package com.fitmesh.fitmeshbackend.adapter.in.web.controller;

import com.fitmesh.fitmeshbackend.adapter.in.web.dto.response.UserProfileResponse;
import com.fitmesh.fitmeshbackend.application.ports.in.AuthenticatedUserProvider;
import com.fitmesh.fitmeshbackend.application.ports.in.GetCurrentUserUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.result.UserProfileResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final AuthenticatedUserProvider authProvider;

    public UserController(
            GetCurrentUserUseCase getCurrentUserUseCase,
            AuthenticatedUserProvider authProvider
    ) {
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.authProvider = authProvider;
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getCurrentUser() {
        // Extract authenticated user ID from JWT
        String userId = authProvider.getCurrentUserId();

        // Get user profile
        UserProfileResult result = getCurrentUserUseCase.getCurrentUser(userId);

        UserProfileResponse response = new UserProfileResponse(
                result.getUserId(),
                result.getEmail(),
                result.getPhoneNumber(),
                result.getFirstname(),
                result.getLastname(),
                result.getRoles(),
                result.getStatus(),
                result.getRegisteredAt(),
                result.getLastLoginAt()
        );

        return ResponseEntity.ok(response);
    }
}