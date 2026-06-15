package com.fitmesh.fitmeshbackend.adapter.in.web.controller;

import com.fitmesh.fitmeshbackend.adapter.in.web.dto.request.OnePipeWebhookRequest;
import com.fitmesh.fitmeshbackend.application.ports.in.ProcessPaymentWebhookUseCase;
import com.fitmesh.fitmeshbackend.application.ports.in.command.ProcessPaymentWebhookCommand;
import com.fitmesh.fitmeshbackend.domain.exception.InvalidPaymentReferenceException;
import com.fitmesh.fitmeshbackend.domain.exception.InvalidWebhookDataException;
import com.fitmesh.fitmeshbackend.domain.exception.PaymentNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/webhooks")
public class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);

    private final ProcessPaymentWebhookUseCase processPaymentWebhookUseCase;

    public WebhookController(
            ProcessPaymentWebhookUseCase processPaymentWebhookUseCase
    ) {
        this.processPaymentWebhookUseCase = processPaymentWebhookUseCase;
    }

    @PostMapping("/onepipe/payment")
    public ResponseEntity<Map<String, String>> handleOnePipeWebhook(
            @RequestBody OnePipeWebhookRequest request
    ) {
        logger.info("Received OnePipe webhook - Transaction: {}, Status: {}",
                request.getTransactionReference(),
                request.getStatus());

        try {
            ProcessPaymentWebhookCommand command = new ProcessPaymentWebhookCommand(
                    request.getTransactionReference(),
                    request.getProviderReference(),
                    request.getStatus()
            );

            processPaymentWebhookUseCase.processWebhook(command);

            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Webhook processed successfully");

            return ResponseEntity.ok(response);

        } catch (InvalidWebhookDataException e) {
            logger.error("Invalid webhook data: {}", e.getMessage());

            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (InvalidPaymentReferenceException e) {
            logger.error("Invalid payment reference: {}", e.getMessage());

            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (PaymentNotFoundException e) {
            logger.error("Payment not found for webhook: {}",
                    request.getTransactionReference(), e);

            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception e) {
            logger.error("Unexpected error processing webhook: {}",
                    request.getTransactionReference(), e);

            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Internal server error: " + e.getClass().getSimpleName());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}