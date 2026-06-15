package com.fitmesh.fitmeshbackend.domain.model;

import com.fitmesh.fitmeshbackend.domain.enums.UserRole;
import com.fitmesh.fitmeshbackend.domain.enums.UserStatus;
import com.fitmesh.fitmeshbackend.domain.exception.InvalidPaymentStateException;
import com.fitmesh.fitmeshbackend.domain.exception.UserCannotInitiatePaymentException;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldCreateUserWithDefaultMemberRole() {
        User user = new User(
                "user-123",
                "test@example.com",
                "1234567890",
                "qerg1234",
                "John",
                "Doe",
                null
        );

        assertTrue(user.hasRole(UserRole.MEMBER));
        assertFalse(user.isAdmin());
        assertEquals(UserStatus.ACTIVE, user.getStatus());
    }

    @Test
    void shouldCreateAdminUser() {
        User user = new User(
                "admin-123",
                "admin@example.com",
                "0987654321",
                "admin1234",
                "John",
                "Doe",
                Set.of(UserRole.ADMIN)
        );

        assertTrue(user.isAdmin());
        assertTrue(user.hasRole(UserRole.ADMIN));
    }

    @Test
    void shouldAllowPaymentWhenUserIsActive() {
        User user = new User(
                "user-123",
                "test@example.com",
                "1234567890",
                "qerg1234",
                "John",
                "Doe",
                Set.of(UserRole.MEMBER)
        );

        assertTrue(user.canInitiatePayment());
        assertDoesNotThrow(user::validateCanInitiatePayment);
    }

    @Test
    void shouldNotAllowPaymentWhenUserIsSuspended() {
        User user = new User(
                "user-123",
                "test@example.com",
                "1234567890",
                "qerg1234",
                "John",
                "Doe",
                Set.of(UserRole.MEMBER)
        );

        user.suspend();

        assertFalse(user.canInitiatePayment());
        assertThrows(
                UserCannotInitiatePaymentException.class,
                user::validateCanInitiatePayment
        );
    }

    @Test
    void shouldReactivateSuspendedUser() {
        User user = new User(
                "user-123",
                "test@example.com",
                "1234567890",
                "qerg1234",
                "John",
                "Doe",
                Set.of(UserRole.MEMBER)
        );

        user.suspend();
        assertEquals(UserStatus.SUSPENDED, user.getStatus());

        user.reactivate();
        assertEquals(UserStatus.ACTIVE, user.getStatus());
        assertTrue(user.canInitiatePayment());
    }

    @Test
    void shouldNotSuspendDeletedUser() {
        User user = new User(
                "user-123",
                "test@example.com",
                "1234567890",
                "qerg1234",
                "John",
                "Doe",
                Set.of(UserRole.MEMBER),
                UserStatus.DELETED,
                java.time.Instant.now(),
                null,
                java.time.Instant.now()
        );

        assertThrows(
                InvalidPaymentStateException.class,
                user::suspend
        );
    }
}