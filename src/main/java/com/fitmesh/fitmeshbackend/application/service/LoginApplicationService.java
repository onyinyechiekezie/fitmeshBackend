package com.fitmesh.fitmeshbackend.application.service;

import com.fitmesh.fitmeshbackend.application.ports.in.LoginUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.command.LoginCommand;
import com.fitmesh.fitmeshbackend.application.ports.in.result.AuthenticationResult;
import com.fitmesh.fitmeshbackend.application.ports.out.PasswordEncoderPort;
import com.fitmesh.fitmeshbackend.application.ports.out.TokenGeneratorPort;
import com.fitmesh.fitmeshbackend.application.ports.out.UserRepository;
import com.fitmesh.fitmeshbackend.domain.exception.InvalidCredentialsException;
import com.fitmesh.fitmeshbackend.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class LoginApplicationService implements LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenGeneratorPort tokenGenerator;

    public LoginApplicationService(
            UserRepository userRepository,
            PasswordEncoderPort passwordEncoder,
            TokenGeneratorPort tokenGenerator
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public AuthenticationResult login(LoginCommand command) {
        // Find user by email
        User user = userRepository.findByEmail(command.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        // Verify password
        if (!passwordEncoder.matches(command.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        // Verify user can login (active status)
        user.validateCanInitiatePayment(); // Reusing existing validation

        // Record login
        user.recordLogin();
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
}
