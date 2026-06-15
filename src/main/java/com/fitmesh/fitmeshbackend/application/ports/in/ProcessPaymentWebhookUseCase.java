package com.fitmesh.fitmeshbackend.application.ports.in;

import com.fitmesh.fitmeshbackend.application.ports.in.command.ProcessPaymentWebhookCommand;

public interface ProcessPaymentWebhookUseCase {

    void processWebhook(ProcessPaymentWebhookCommand command);
}
