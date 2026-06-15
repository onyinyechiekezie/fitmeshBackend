package com.fitmesh.fitmeshbackend.application.service;

import com.fitmesh.fitmeshbackend.application.ports.in.GetCurrentUserUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.result.UserProfileResult;
import com.fitmesh.fitmeshbackend.application.ports.out.UserRepository;
import com.fitmesh.fitmeshbackend.domain.exception.UserNotFoundException;
import com.fitmesh.fitmeshbackend.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class GetCurrentUserApplicationService implements GetCurrentUserUseCase {

    private final UserRepository userRepository;

    public GetCurrentUserApplicationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserProfileResult getCurrentUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return new UserProfileResult(
                user.getUserId(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getFirstname(),
                user.getLastname(),
                user.getRoles(),
                user.getStatus(),
                user.getRegisteredAt(),
                user.getLastLoginAt()
        );
    }
}