package com.fitmesh.fitmeshbackend.adapter.out.persistence.repository;

import com.fitmesh.fitmeshbackend.adapter.out.persistence.entity.MembershipJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipJpaRepository extends JpaRepository<MembershipJpaEntity, String> {

    List<MembershipJpaEntity> findByUserId(String userId);

    Optional<MembershipJpaEntity> findByUserIdAndStatus(String userId, String status);
}
