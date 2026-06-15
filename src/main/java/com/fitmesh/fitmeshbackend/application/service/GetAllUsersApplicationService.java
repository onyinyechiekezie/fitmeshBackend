package com.fitmesh.fitmeshbackend.application.service;

import com.fitmesh.fitmeshbackend.application.ports.in.GetAllUsersUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.result.AdminUserResult;
import com.fitmesh.fitmeshbackend.application.ports.out.UserRepository;
import com.fitmesh.fitmeshbackend.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllUsersApplicationService implements GetAllUsersUseCase {

    private final UserRepository userRepository;

    public GetAllUsersApplicationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<AdminUserResult> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResult)
                .collect(Collectors.toList());
    }

    private AdminUserResult toResult(User user) {
        return new AdminUserResult(
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
