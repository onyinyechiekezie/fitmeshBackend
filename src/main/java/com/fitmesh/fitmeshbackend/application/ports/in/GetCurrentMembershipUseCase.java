package com.fitmesh.fitmeshbackend.application.ports.in;

import com.fitmesh.fitmeshbackend.application.ports.in.result.MembershipResult;

import java.util.Optional;

public interface GetCurrentMembershipUseCase {

    Optional<MembershipResult> getCurrentMembership(String userId);
}