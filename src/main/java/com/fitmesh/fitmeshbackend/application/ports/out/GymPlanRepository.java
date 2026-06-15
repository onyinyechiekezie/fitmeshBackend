package com.fitmesh.fitmeshbackend.application.ports.out;

import com.fitmesh.fitmeshbackend.domain.model.GymPlan;

import java.util.List;
import java.util.Optional;

public interface GymPlanRepository {

    Optional<GymPlan> findById(String planId);

    List<GymPlan> findAllActive();

    void save(GymPlan gymPlan);
}