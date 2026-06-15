package com.fitmesh.fitmeshbackend.application.service;

import com.fitmesh.fitmeshbackend.application.ports.in.GetGymPlansUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.result.GymPlanResult;
import com.fitmesh.fitmeshbackend.application.ports.out.GymPlanRepository;
import com.fitmesh.fitmeshbackend.domain.model.GymPlan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetGymPlansApplicationService implements GetGymPlansUseCase {

    private final GymPlanRepository gymPlanRepository;

    public GetGymPlansApplicationService(GymPlanRepository gymPlanRepository) {
        this.gymPlanRepository = gymPlanRepository;
    }

    @Override
    public List<GymPlanResult> getAllActivePlans() {
        return gymPlanRepository.findAllActive().stream()
                .map(this::toResult)
                .collect(Collectors.toList());
    }

    private GymPlanResult toResult(GymPlan gymPlan) {
        return new GymPlanResult(
                gymPlan.getPlanId(),
                gymPlan.getName(),
                gymPlan.getDescription(),
                gymPlan.getPrice(),
                gymPlan.getCurrency(),
                gymPlan.getBillingFrequency(),
                gymPlan.getDurationInDays(),
                gymPlan.getFeatures()
        );
    }
}
