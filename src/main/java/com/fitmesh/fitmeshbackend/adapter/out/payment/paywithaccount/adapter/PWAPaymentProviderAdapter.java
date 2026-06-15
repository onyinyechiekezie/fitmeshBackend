package com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.dto.request.PWASendInvoiceRequest;
import com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.dto.response.PWAResponse;
import com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.exception.PWASignatureGenerationException;
import com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.mapper.PaymentToPwaSendInvoiceRequestMapper;
import com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.mapper.PWAResponseMapper;
import com.fitmesh.fitmeshbackend.application.ports.out.PaymentProviderPort;
import com.fitmesh.fitmeshbackend.application.ports.out.command.ProviderPaymentRequest;
import com.fitmesh.fitmeshbackend.application.ports.out.result.ProviderPaymentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.MessageDigest;

@Component
public class PWAPaymentProviderAdapter implements PaymentProviderPort {

    private static final Logger logger = LoggerFactory.getLogger(PWAPaymentProviderAdapter.class);

    private final WebClient webClient;
    private final String secretKey;
    private final PaymentToPwaSendInvoiceRequestMapper requestMapper;
    private final PWAResponseMapper responseMapper;
    private final ObjectMapper objectMapper; // Add this

    public PWAPaymentProviderAdapter(
            WebClient webClient,
            @Value("${onepipe.secret-key}") String secretKey,
            PaymentToPwaSendInvoiceRequestMapper requestMapper,
            PWAResponseMapper responseMapper
    ) {
        this.webClient = webClient;
        this.secretKey = secretKey;
        this.requestMapper = requestMapper;
        this.responseMapper = responseMapper;
        this.objectMapper = new ObjectMapper(); // Initialize
    }

    @Override
    public ProviderPaymentResponse initiatePayment(ProviderPaymentRequest request) {
        logger.info("Initiating payment with OnePipe for reference: {}", request.getReference());

        // Map domain request to OnePipe-specific request
        PWASendInvoiceRequest pwaRequest = requestMapper.map(request);

        // LOG THE EXACT REQUEST BEING SENT (temporary debug)
        try {
            String jsonRequest = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(pwaRequest);
            logger.info("OnePipe Request JSON:\n{}", jsonRequest);
        } catch (Exception e) {
            logger.warn("Could not serialize request for logging", e);
        }

        // Generate signature
        String signature = generateSignature(request.getReference());
        logger.debug("Generated signature for reference: {}", request.getReference());

        // Call OnePipe API
        PWAResponse pwaResponse = webClient.post()
                .uri("/v2/transact")
                .header("Signature", signature)
                .bodyValue(pwaRequest)
                .retrieve()
                .bodyToMono(PWAResponse.class)
                .doOnSuccess(response -> {
                    logger.info("OnePipe response received: {}", response.getStatus());
                    // LOG THE EXACT RESPONSE (temporary debug)
                    try {
                        String jsonResponse = objectMapper.writerWithDefaultPrettyPrinter()
                                .writeValueAsString(response);
                        logger.info("OnePipe Response JSON:\n{}", jsonResponse);
                    } catch (Exception e) {
                        logger.warn("Could not serialize response for logging", e);
                    }
                })
                .doOnError(error -> logger.error("OnePipe request failed", error))
                .block();

        if (pwaResponse == null) {
            throw new RuntimeException("OnePipe returned null response");
        }

        // Map OnePipe response to domain response
        ProviderPaymentResponse providerResponse = responseMapper.mapFromPwa(pwaResponse);

        logger.info("Payment initiated. Provider reference: {}, Status: {}",
                providerResponse.getProviderReference(),
                providerResponse.getStatus());

        return providerResponse;
    }

    private String generateSignature(String reference) {
        try {
            String value = reference + ";" + secretKey;

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(value.getBytes());

            StringBuilder hex = new StringBuilder();
            for (byte b : digest) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();

        } catch (Exception e) {
            throw new PWASignatureGenerationException("Failed to generate PWA signature", e);
        }
    }
}