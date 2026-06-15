package com.fitmesh.fitmeshbackend.adapter.in.web.mapper;

import com.fitmesh.fitmeshbackend.adapter.in.web.dto.request.InitiatePayWithAccountRequest;
import com.fitmesh.fitmeshbackend.adapter.in.web.dto.response.InitiatePaymentResponse;
import com.fitmesh.fitmeshbackend.application.ports.in.AuthenticatedUserProvider;
import com.fitmesh.fitmeshbackend.application.ports.in.InitiatePayWithAccountUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.command.InitiatePayWithAccountCommand;
import com.fitmesh.fitmeshbackend.application.ports.in.result.InitiatePaymentResult;
import org.springframework.stereotype.Component;

@Component
public class InitiatePayWithAccountWebMapper {

    private final InitiatePayWithAccountUseCase useCase;
    private final AuthenticatedUserProvider authProvider;

    public InitiatePayWithAccountWebMapper(
            InitiatePayWithAccountUseCase useCase,
            AuthenticatedUserProvider authProvider
    ) {
        this.useCase = useCase;
        this.authProvider = authProvider;
    }

    public InitiatePaymentResponse initiatePayWithAccount(
            InitiatePayWithAccountRequest request
    ) {
        // Get authenticated user ID from security context
        String authenticatedUserId = authProvider.getCurrentUserId();

        InitiatePayWithAccountCommand command =
                new InitiatePayWithAccountCommand(
                        authenticatedUserId,
                        request.getAmount(),
                        request.getCurrency(),
                        request.getPaymentType(),
                        request.getDescription()
                );

        InitiatePaymentResult result = useCase.initiate(command);

        return new InitiatePaymentResponse(
                result.getPaymentId(),
                result.getPaymentReference(),
                result.getProviderStatus(),
                result.getRedirectUrl(),
                result.getInitiatedAt()
        );
    }
}
