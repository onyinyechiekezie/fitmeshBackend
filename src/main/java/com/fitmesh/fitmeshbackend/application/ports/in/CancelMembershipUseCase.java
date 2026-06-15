package com.fitmesh.fitmeshbackend.application.ports.in;

import com.fitmesh.fitmeshbackend.application.ports.in.command.CancelMembershipCommand;

public interface CancelMembershipUseCase {

    void cancelMembership(CancelMembershipCommand command);
}
