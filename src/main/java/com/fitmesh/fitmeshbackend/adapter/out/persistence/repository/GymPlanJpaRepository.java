package com.fitmesh.fitmeshbackend.adapter.out.persistence.repository;

import com.fitmesh.fitmeshbackend.adapter.out.persistence.entity.GymPlanJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GymPlanJpaRepository extends JpaRepository<GymPlanJpaEntity, String> {

    List<GymPlanJpaEntity> findByIsActiveTrue();
}
