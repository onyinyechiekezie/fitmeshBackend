package com.fitmesh.fitmeshbackend.application.ports.in;

import com.fitmesh.fitmeshbackend.application.ports.in.command.PauseMembershipCommand;

public interface PauseMembershipUseCase {

    void pauseMembership(PauseMembershipCommand command);
}
