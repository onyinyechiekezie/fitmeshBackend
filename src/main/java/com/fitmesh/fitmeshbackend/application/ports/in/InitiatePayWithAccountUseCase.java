package com.fitmesh.fitmeshbackend.application.ports.in;

import com.fitmesh.fitmeshbackend.application.ports.in.command.InitiatePayWithAccountCommand;
import com.fitmesh.fitmeshbackend.application.ports.in.result.InitiatePaymentResult;
import org.springframework.stereotype.Service;

public interface InitiatePayWithAccountUseCase {

    InitiatePaymentResult initiate (InitiatePayWithAccountCommand command);
}

