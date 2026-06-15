package com.fitmesh.fitmeshbackend.application.ports.in;

import com.fitmesh.fitmeshbackend.domain.enums.UserRole;

import java.util.Set;

public interface AuthenticatedUserProvider {

    /**
     * Returns the currently authenticated user's ID.
     * This method is called by inbound adapters (controllers)
     * after authentication has been verified.
     *
     * @return the authenticated user ID
     * @throws com.fitmesh.fitmeshbackend.shared.exception.UnauthenticatedException
     *         if no authenticated user exists in the current context
     */
    String getCurrentUserId();

    /**
     * Returns the currently authenticated user's roles.
     *
     * @return set of roles for the authenticated user
     */
    Set<UserRole> getCurrentUserRoles();

    /**
     * Checks if the currently authenticated user has a specific role.
     *
     * @param role the role to check
     * @return true if user has the role, false otherwise
     */
    boolean hasRole(UserRole role);

    /**
     * Checks if the currently authenticated user is an admin.
     *
     * @return true if user has ADMIN role, false otherwise
     */
    boolean isAdmin();
}