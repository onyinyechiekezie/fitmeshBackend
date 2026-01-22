package com.fitmesh.fitmeshbackend.application.ports.in;

import com.fitmesh.fitmeshbackend.application.ports.in.command.InitiatePayWithAccountCommand;
import com.fitmesh.fitmeshbackend.application.ports.in.result.InitiatePaymentResult;
public interface InitiatePayWithAccountUseCase {

    InitiatePaymentResult initiate (InitiatePayWithAccountCommand command);
}

