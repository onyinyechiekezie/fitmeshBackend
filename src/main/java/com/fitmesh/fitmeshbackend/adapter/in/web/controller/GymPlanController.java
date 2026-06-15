package com.fitmesh.fitmeshbackend.adapter.in.web.controller;

import com.fitmesh.fitmeshbackend.application.ports.in.GetGymPlansUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.result.GymPlanResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gym-plans")
public class GymPlanController {

    private final GetGymPlansUseCase getGymPlansUseCase;

    public GymPlanController(GetGymPlansUseCase getGymPlansUseCase) {
        this.getGymPlansUseCase = getGymPlansUseCase;
    }

    @GetMapping
    public ResponseEntity<List<GymPlanResult>> getAllActivePlans() {
        List<GymPlanResult> plans = getGymPlansUseCase.getAllActivePlans();
        return ResponseEntity.ok(plans);
    }
}
