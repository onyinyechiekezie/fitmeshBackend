package com.fitmesh.fitmeshbackend.application.ports.in;

import com.fitmesh.fitmeshbackend.application.ports.in.command.LoginCommand;
import com.fitmesh.fitmeshbackend.application.ports.in.result.AuthenticationResult;

public interface LoginUseCase {

    AuthenticationResult login(LoginCommand command);
}
