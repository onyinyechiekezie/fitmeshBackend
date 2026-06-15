package com.fitmesh.fitmeshbackend.adapter.out.persistence.adapter;

import com.fitmesh.fitmeshbackend.adapter.out.persistence.mapper.MembershipEntityMapper;
import com.fitmesh.fitmeshbackend.adapter.out.persistence.repository.MembershipJpaRepository;
import com.fitmesh.fitmeshbackend.application.ports.out.MembershipRepository;
import com.fitmesh.fitmeshbackend.domain.enums.MembershipStatus;
import com.fitmesh.fitmeshbackend.domain.model.Membership;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaMembershipRepositoryAdapter implements MembershipRepository {

    private final MembershipJpaRepository jpaRepository;
    private final MembershipEntityMapper mapper;

    public JpaMembershipRepositoryAdapter(
            MembershipJpaRepository jpaRepository,
            MembershipEntityMapper mapper
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Membership membership) {
        jpaRepository.save(mapper.toEntity(membership));
    }

    @Override
    public Optional<Membership> findById(String membershipId) {
        return jpaRepository.findById(membershipId)
                .map(mapper::toDomain);
    }

    @Override
    public List<Membership> findByUserId(String userId) {
        return jpaRepository.findByUserId(userId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Membership> findActiveByUserId(String userId) {
        return jpaRepository.findByUserIdAndStatus(userId, MembershipStatus.ACTIVE.name())
                .map(mapper::toDomain);
    }

    @Override
    public List<Membership> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
