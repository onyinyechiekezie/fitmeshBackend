package com.fitmesh.fitmeshbackend.application.ports.in;

import com.fitmesh.fitmeshbackend.application.ports.in.result.UserProfileResult;

public interface GetCurrentUserUseCase {

    UserProfileResult getCurrentUser(String userId);
}