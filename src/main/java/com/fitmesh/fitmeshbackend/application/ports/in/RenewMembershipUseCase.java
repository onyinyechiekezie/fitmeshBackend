package com.fitmesh.fitmeshbackend.application.ports.in;

import com.fitmesh.fitmeshbackend.application.ports.in.command.RenewMembershipCommand;
import com.fitmesh.fitmeshbackend.application.ports.in.result.RenewalResult;

public interface RenewMembershipUseCase {

    RenewalResult renewMembership(RenewMembershipCommand command);
}
