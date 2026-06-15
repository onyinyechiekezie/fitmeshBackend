package com.fitmesh.fitmeshbackend.adapter.out.persistence.mapper;

import com.fitmesh.fitmeshbackend.adapter.out.persistence.entity.GymPlanJpaEntity;
import com.fitmesh.fitmeshbackend.domain.enums.BillingFrequency;
import com.fitmesh.fitmeshbackend.domain.enums.Currency;
import com.fitmesh.fitmeshbackend.domain.model.GymPlan;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GymPlanEntityMapper {

    /**
     * Convert domain GymPlan to JPA entity
     */
    public GymPlanJpaEntity toEntity(GymPlan gymPlan) {
        GymPlanJpaEntity entity = new GymPlanJpaEntity();

        entity.setPlanId(gymPlan.getPlanId());
        entity.setName(gymPlan.getName());
        entity.setDescription(gymPlan.getDescription());
        entity.setPrice(gymPlan.getPrice());
        entity.setCurrency(gymPlan.getCurrency().name());
        entity.setBillingFrequency(gymPlan.getBillingFrequency().name());
        entity.setDurationInDays(gymPlan.getDurationInDays());

        // Convert features list to comma-separated string
        String featuresString = String.join(",", gymPlan.getFeatures());
        entity.setFeatures(featuresString);

        entity.setActive(gymPlan.isActive());
        entity.setCreatedAt(gymPlan.getCreatedAt());
        entity.setUpdatedAt(gymPlan.getUpdatedAt());

        return entity;
    }

    /**
     * Convert JPA entity to domain GymPlan
     */
    public GymPlan toDomain(GymPlanJpaEntity entity) {
        // Parse features from comma-separated string
        List<String> features = parseFeatures(entity.getFeatures());

        // Use reconstruction constructor
        return new GymPlan(
                entity.getPlanId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                Currency.valueOf(entity.getCurrency()),
                BillingFrequency.valueOf(entity.getBillingFrequency()),
                entity.getDurationInDays(),
                features,
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    private List<String> parseFeatures(String featuresString) {
        if (featuresString == null || featuresString.trim().isEmpty()) {
            return Collections.emptyList();
        }

        return Arrays.stream(featuresString.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
