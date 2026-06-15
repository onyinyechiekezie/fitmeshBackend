package com.fitmesh.fitmeshbackend.adapter.out.persistence.mapper;

import com.fitmesh.fitmeshbackend.adapter.out.persistence.entity.MembershipJpaEntity;
import com.fitmesh.fitmeshbackend.domain.enums.MembershipStatus;
import com.fitmesh.fitmeshbackend.domain.model.Membership;
import org.springframework.stereotype.Component;

@Component
public class MembershipEntityMapper {

    /**
     * Convert domain Membership to JPA entity
     */
    public MembershipJpaEntity toEntity(Membership membership) {
        MembershipJpaEntity entity = new MembershipJpaEntity();

        entity.setMembershipId(membership.getMembershipId());
        entity.setUserId(membership.getUserId());
        entity.setPlanId(membership.getPlanId());
        entity.setStatus(membership.getStatus().name());
        entity.setStartDate(membership.getStartDate());
        entity.setEndDate(membership.getEndDate());
        entity.setLastPaymentDate(membership.getLastPaymentDate());
        entity.setCreatedAt(membership.getCreatedAt());
        entity.setUpdatedAt(membership.getUpdatedAt());

        return entity;
    }

    /**
     * Convert JPA entity to domain Membership
     */
    public Membership toDomain(MembershipJpaEntity entity) {
        // Use reconstruction constructor
        return new Membership(
                entity.getMembershipId(),
                entity.getUserId(),
                entity.getPlanId(),
                MembershipStatus.valueOf(entity.getStatus()),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getLastPaymentDate(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
