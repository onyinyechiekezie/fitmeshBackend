package com.fitmesh.fitmeshbackend.infrastructure.security;

import com.fitmesh.fitmeshbackend.application.ports.out.TokenGeneratorPort;
import com.fitmesh.fitmeshbackend.domain.enums.UserRole;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class JwtTokenGeneratorAdapter implements TokenGeneratorPort {

    private final JwtService jwtService;

    public JwtTokenGeneratorAdapter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public String generateToken(String userId, String email, Set<UserRole> roles) {
        return jwtService.generateToken(userId, email, roles);
    }
}

