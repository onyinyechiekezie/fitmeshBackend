package com.fitmesh.fitmeshbackend.application.ports.out;

import com.fitmesh.fitmeshbackend.domain.model.Membership;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository {

    void save(Membership membership);

    Optional<Membership> findById(String membershipId);

    List<Membership> findByUserId(String userId);

    Optional<Membership> findActiveByUserId(String userId);

    List<Membership> findAll();
}
