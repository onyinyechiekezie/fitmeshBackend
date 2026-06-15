package com.fitmesh.fitmeshbackend.application.ports.out;

import com.fitmesh.fitmeshbackend.domain.enums.UserRole;

import java.util.Set;

public interface TokenGeneratorPort {

    String generateToken(String userId, String email, Set<UserRole> roles);
}
