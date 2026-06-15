package com.fitmesh.fitmeshbackend.application.ports.in;

import com.fitmesh.fitmeshbackend.application.ports.in.command.ResumeMembershipCommand;

public interface ResumeMembershipUseCase {

    void resumeMembership(ResumeMembershipCommand command);
}