package com.fitmesh.fitmeshbackend.infrastructure.security;

import com.fitmesh.fitmeshbackend.application.ports.in.AuthenticatedUserProvider;
import com.fitmesh.fitmeshbackend.domain.enums.UserRole;
import com.fitmesh.fitmeshbackend.shared.exception.UnauthenticatedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SpringSecurityAuthenticatedUserProvider
        implements AuthenticatedUserProvider {

    @Override
    public String getCurrentUserId() {
        Authentication authentication = getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthenticatedException("No authenticated user in context");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof String)) {
            throw new UnauthenticatedException("Invalid authentication principal");
        }

        return (String) principal;
    }

    @Override
    public Set<UserRole> getCurrentUserRoles() {
        Authentication authentication = getAuthentication();

        if (authentication == null) {
            throw new UnauthenticatedException("No authenticated user in context");
        }

        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_"))
                .map(auth -> auth.substring(5)) // Remove "ROLE_" prefix
                .map(UserRole::valueOf)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean hasRole(UserRole role) {
        try {
            return getCurrentUserRoles().contains(role);
        } catch (UnauthenticatedException e) {
            return false;
        }
    }

    @Override
    public boolean isAdmin() {
        return hasRole(UserRole.ADMIN);
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
