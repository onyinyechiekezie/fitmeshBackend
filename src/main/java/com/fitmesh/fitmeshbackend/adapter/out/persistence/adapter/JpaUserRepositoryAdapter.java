package com.fitmesh.fitmeshbackend.adapter.out.persistence.adapter;

import com.fitmesh.fitmeshbackend.adapter.out.persistence.entity.UserJpaEntity;
import com.fitmesh.fitmeshbackend.adapter.out.persistence.mapper.UserEntityMapper;
import com.fitmesh.fitmeshbackend.adapter.out.persistence.repository.UserJpaRepository;
import com.fitmesh.fitmeshbackend.application.ports.out.UserRepository;
import com.fitmesh.fitmeshbackend.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaUserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserEntityMapper mapper;

    public JpaUserRepositoryAdapter(
            UserJpaRepository jpaRepository,
            UserEntityMapper mapper
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(User user) {
        UserJpaEntity entity = mapper.toEntity(user);
        jpaRepository.save(entity);
    }

    @Override
    public Optional<User> findById(String userId) {
        return jpaRepository.findById(userId)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return jpaRepository.findByPhoneNumber(phoneNumber)
                .map(mapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return jpaRepository.existsByPhoneNumber(phoneNumber);
    }
}