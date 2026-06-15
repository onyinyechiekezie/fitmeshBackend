package com.fitmesh.fitmeshbackend.adapter.in.web.controller;

import com.fitmesh.fitmeshbackend.adapter.in.web.dto.request.LoginRequest;
import com.fitmesh.fitmeshbackend.adapter.in.web.dto.request.RegisterRequest;
import com.fitmesh.fitmeshbackend.adapter.in.web.dto.response.AuthenticationResponse;
import com.fitmesh.fitmeshbackend.application.ports.in.LoginUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.RegisterUserUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.command.LoginCommand;
import com.fitmesh.fitmeshbackend.application.ports.in.command.RegisterUserCommand;
import com.fitmesh.fitmeshbackend.application.ports.in.result.AuthenticationResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;

    public AuthenticationController(
            RegisterUserUseCase registerUserUseCase,
            LoginUseCase loginUseCase
    ) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        RegisterUserCommand command = new RegisterUserCommand(
                request.getEmail(),
                request.getPhoneNumber(),
                request.getPassword(),
                request.getFirstname(),
                request.getLastname()
        );

        AuthenticationResult result = registerUserUseCase.register(command);

        AuthenticationResponse response = new AuthenticationResponse(
                result.getUserId(),
                result.getEmail(),
                result.getToken()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginRequest request
    ) {
        LoginCommand command = new LoginCommand(
                request.getEmail(),
                request.getPassword()
        );

        AuthenticationResult result = loginUseCase.login(command);

        AuthenticationResponse response = new AuthenticationResponse(
                result.getUserId(),
                result.getEmail(),
                result.getToken()
        );

        return ResponseEntity.ok(response);
    }
}