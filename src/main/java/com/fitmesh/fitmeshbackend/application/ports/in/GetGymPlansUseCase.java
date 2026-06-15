package com.fitmesh.fitmeshbackend.application.ports.in;

import com.fitmesh.fitmeshbackend.application.ports.in.result.GymPlanResult;

import java.util.List;

public interface GetGymPlansUseCase {

    List<GymPlanResult> getAllActivePlans();
}