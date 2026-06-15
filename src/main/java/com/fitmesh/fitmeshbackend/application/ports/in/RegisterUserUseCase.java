package com.fitmesh.fitmeshbackend.application.ports.in;

import com.fitmesh.fitmeshbackend.application.ports.in.command.RegisterUserCommand;
import com.fitmesh.fitmeshbackend.application.ports.in.result.AuthenticationResult;

public interface RegisterUserUseCase {

    AuthenticationResult register(RegisterUserCommand command);
}
