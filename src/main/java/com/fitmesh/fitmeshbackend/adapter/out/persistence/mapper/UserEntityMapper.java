package com.fitmesh.fitmeshbackend.adapter.out.persistence.mapper;

import com.fitmesh.fitmeshbackend.adapter.out.persistence.entity.UserJpaEntity;
import com.fitmesh.fitmeshbackend.domain.enums.UserRole;
import com.fitmesh.fitmeshbackend.domain.enums.UserStatus;
import com.fitmesh.fitmeshbackend.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserEntityMapper {

    /**
     * Convert domain User to JPA entity
     */
    public UserJpaEntity toEntity(User user) {
        UserJpaEntity entity = new UserJpaEntity();

        entity.setUserId(user.getUserId());
        entity.setEmail(user.getEmail());
        entity.setPhoneNumber(user.getPhoneNumber());
        entity.setPasswordHash(user.getPasswordHash());
        entity.setFirstname(user.getFirstname());
        entity.setLastname(user.getLastname());
        entity.setStatus(user.getStatus().name());

        // Convert roles
        Set<String> roleStrings = user.getRoles().stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
        entity.setRoles(roleStrings);

        entity.setRegisteredAt(user.getRegisteredAt());
        entity.setLastLoginAt(user.getLastLoginAt());
        entity.setUpdatedAt(user.getUpdatedAt());

        return entity;
    }

    /**
     * Convert JPA entity to domain User
     */
    public User toDomain(UserJpaEntity entity) {
        // Convert role strings to enums
        Set<UserRole> roles = entity.getRoles().stream()
                .map(UserRole::valueOf)
                .collect(Collectors.toSet());

        // Use reconstruction constructor
        return new User(
                entity.getUserId(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getPasswordHash(),
                entity.getFirstname(),
                entity.getLastname(),
                roles,
                UserStatus.valueOf(entity.getStatus()),
                entity.getRegisteredAt(),
                entity.getLastLoginAt(),
                entity.getUpdatedAt()
        );
    }
}
