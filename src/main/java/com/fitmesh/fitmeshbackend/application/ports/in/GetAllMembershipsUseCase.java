package com.fitmesh.fitmeshbackend.application.ports.in;

import com.fitmesh.fitmeshbackend.application.ports.in.result.AdminMembershipResult;

import java.util.List;

public interface GetAllMembershipsUseCase {

    List<AdminMembershipResult> getAllMemberships();
}

