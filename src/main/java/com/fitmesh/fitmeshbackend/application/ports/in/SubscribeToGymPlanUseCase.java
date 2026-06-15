package com.fitmesh.fitmeshbackend.application.ports.in;

import com.fitmesh.fitmeshbackend.application.ports.in.command.SubscribeToGymPlanCommand;
import com.fitmesh.fitmeshbackend.application.ports.in.result.SubscriptionResult;

public interface SubscribeToGymPlanUseCase {

    SubscriptionResult subscribe(SubscribeToGymPlanCommand command);
}
