package com.fitmesh.fitmeshbackend.application.ports.in;

import com.fitmesh.fitmeshbackend.application.ports.in.result.AdminUserResult;

import java.util.List;

public interface GetAllUsersUseCase {

    List<AdminUserResult> getAllUsers();
}