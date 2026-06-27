package com.fitmesh.fitmeshbackend.application.service;

import com.fitmesh.fitmeshbackend.application.ports.in.RegisterUserUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.command.RegisterUserCommand;
import com.fitmesh.fitmeshbackend.application.ports.in.result.AuthenticationResult;
import com.fitmesh.fitmeshbackend.application.ports.out.PasswordEncoderPort;
import com.fitmesh.fitmeshbackend.application.ports.out.TokenGeneratorPort;
import com.fitmesh.fitmeshbackend.application.ports.out.UserRepository;
import com.fitmesh.fitmeshbackend.domain.enums.UserRole;
import com.fitmesh.fitmeshbackend.domain.exception.InvalidPasswordException;
import com.fitmesh.fitmeshbackend.domain.exception.UserAlreadyExistsException;
import com.fitmesh.fitmeshbackend.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class RegisterUserApplicationService implements RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenGeneratorPort tokenGenerator;

    public RegisterUserApplicationService(
            UserRepository userRepository,
            PasswordEncoderPort passwordEncoder,
            TokenGeneratorPort tokenGenerator
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public AuthenticationResult register(RegisterUserCommand command) {
        // Validate email uniqueness
        if (userRepository.existsByEmail(command.getEmail())) {
            throw new UserAlreadyExistsException("email", command.getEmail());
        }

        // Validate phone number uniqueness
        if (userRepository.existsByPhoneNumber(command.getPhoneNumber())) {
            throw new UserAlreadyExistsException("phone number", command.getPhoneNumber());
        }

        // Validate password strength
        validatePassword(command.getPassword());

        // Hash password
        String passwordHash = passwordEncoder.encode(command.getPassword());

        // Create user
        User user = new User(
                UUID.randomUUID().toString(),
                command.getEmail(),
                command.getPhoneNumber(),
                passwordHash,
                command.getFirstname(),
                command.getLastname(),
                Set.of(UserRole.MEMBER) // Default role
        );

        // Save user
        userRepository.save(user);

        // Generate JWT token
        String token = tokenGenerator.generateToken(
                user.getUserId(),
                user.getEmail(),
                user.getRoles()
        );

        return new AuthenticationResult(
                user.getUserId(),
                user.getEmail(),
                token
        );
    }

    private void validatePassword(String password) {
        if (password.length() < 8) {
            throw new InvalidPasswordException("Password must be at least 8 characters");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new InvalidPasswordException("Password must contain uppercase letter");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new InvalidPasswordException("Password must contain lowercase letter");
        }
        if (!password.matches(".*[0-9].*")) {
            throw new InvalidPasswordException("Password must contain number");
        }
        if (!password.matches(".*[!@#$%^&*].*")) {
            throw new InvalidPasswordException("Password must contain special character");
        }
    }
}