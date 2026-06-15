package com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.mapper;



import com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.dto.response.PWAErrorResponse;
import com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.dto.response.PWAProviderResponse;
import com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.dto.response.PWAResponse;
import com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.dto.response.PWAResponseData;
import com.fitmesh.fitmeshbackend.application.ports.out.result.ProviderPaymentResponse;
import com.fitmesh.fitmeshbackend.domain.exception.InvalidPaymentReferenceException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PWAResponseMapper {

    public ProviderPaymentResponse mapFromPwa(PWAResponse pwaResponse) {
        if (pwaResponse == null) {
            throw new InvalidPaymentReferenceException("PWA response cannot be null");
        }

        PWAResponseData data = pwaResponse.getData();
        if (data == null) {
            throw new InvalidPaymentReferenceException("PWA response data cannot be null");
        }

        // Check if this is an error response
        if ("Failed".equalsIgnoreCase(pwaResponse.getStatus())) {
            return handleErrorResponse(pwaResponse, data);
        }

        // Handle success response
        PWAProviderResponse providerResponse = data.getProviderResponse();
        if (providerResponse == null) {
            throw new InvalidPaymentReferenceException(
                    "PWA provider response cannot be null for successful requests"
            );
        }

        return handleSuccessResponse(pwaResponse, providerResponse);
    }

    private ProviderPaymentResponse handleSuccessResponse(
            PWAResponse pwaResponse,
            PWAProviderResponse providerResponse
    ) {
        // Extract provider reference
        String providerReference = providerResponse.getReference();
        if (providerReference == null || providerReference.isBlank()) {
            throw new InvalidPaymentReferenceException(
                    "PWA provider reference cannot be null or blank"
            );
        }

        // Extract status
        String status = providerResponse.getStatus();
        if (status == null) {
            status = "UNKNOWN";
        }

        // Extract redirect URL (activation_url from meta)
        String redirectUrl = null;
        if (providerResponse.getMeta() != null) {
            redirectUrl = providerResponse.getMeta().getActivationUrl();
        }

        // Extract message
        String message = pwaResponse.getMessage();

        return new ProviderPaymentResponse(
                providerReference,
                status,
                redirectUrl,
                message
        );
    }

    private ProviderPaymentResponse handleErrorResponse(
            PWAResponse pwaResponse,
            PWAResponseData data
    ) {
        // Build error message from errors or error field
        String errorMessage = buildErrorMessage(pwaResponse, data);

        // For failed requests, we don't have a real provider reference
        // Use a placeholder that indicates failure
        String providerReference = "ERROR-" + System.currentTimeMillis();

        return new ProviderPaymentResponse(
                providerReference,
                "FAILED",
                null,
                errorMessage
        );
    }

    private String buildErrorMessage(PWAResponse pwaResponse, PWAResponseData data) {
        StringBuilder errorMsg = new StringBuilder();

        // Add main message if available
        if (pwaResponse.getMessage() != null) {
            errorMsg.append(pwaResponse.getMessage());
        }

        // Add error details from errors array
        if (data.getErrors() != null && !data.getErrors().isEmpty()) {
            List<String> errorMessages = data.getErrors().stream()
                    .map(err -> err.getCode() + ": " + err.getMessage())
                    .collect(Collectors.toList());

            if (errorMsg.length() > 0) {
                errorMsg.append(" - ");
            }
            errorMsg.append(String.join(", ", errorMessages));
        }

        // Add error from single error field
        if (data.getError() != null) {
            if (errorMsg.length() > 0) {
                errorMsg.append(" - ");
            }
            errorMsg.append(data.getError().getCode())
                    .append(": ")
                    .append(data.getError().getMessage());
        }

        // Fallback if no error details found
        if (errorMsg.length() == 0) {
            errorMsg.append("Payment failed - no error details provided by OnePipe");
        }

        return errorMsg.toString();
    }
}