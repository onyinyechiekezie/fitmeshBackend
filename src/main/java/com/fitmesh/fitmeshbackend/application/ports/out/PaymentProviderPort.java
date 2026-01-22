package com.fitmesh.fitmeshbackend.application.ports.out;

import com.fitmesh.fitmeshbackend.application.ports.out.command.ProviderPaymentRequest;
import com.fitmesh.fitmeshbackend.application.ports.out.result.ProviderPaymentResponse;

public interface PaymentProviderPort {

    ProviderPaymentResponse initiatePayment(ProviderPaymentRequest request);


}
