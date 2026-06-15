package com.fitmesh.fitmeshbackend.adapter.out.persistence.adapter;

import com.fitmesh.fitmeshbackend.adapter.out.persistence.mapper.GymPlanEntityMapper;
import com.fitmesh.fitmeshbackend.adapter.out.persistence.repository.GymPlanJpaRepository;
import com.fitmesh.fitmeshbackend.application.ports.out.GymPlanRepository;
import com.fitmesh.fitmeshbackend.domain.model.GymPlan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaGymPlanRepositoryAdapter implements GymPlanRepository {

    private final GymPlanJpaRepository jpaRepository;
    private final GymPlanEntityMapper mapper;

    public JpaGymPlanRepositoryAdapter(
            GymPlanJpaRepository jpaRepository,
            GymPlanEntityMapper mapper
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<GymPlan> findById(String planId) {
        return jpaRepository.findById(planId)
                .map(mapper::toDomain);
    }

    @Override
    public List<GymPlan> findAllActive() {
        return jpaRepository.findByIsActiveTrue().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void save(GymPlan gymPlan) {
        jpaRepository.save(mapper.toEntity(gymPlan));
    }
}
