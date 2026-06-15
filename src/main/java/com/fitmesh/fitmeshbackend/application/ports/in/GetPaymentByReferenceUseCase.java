package com.fitmesh.fitmeshbackend.application.ports.in;

import com.fitmesh.fitmeshbackend.application.ports.in.result.PaymentResult;

public interface GetPaymentByReferenceUseCase {

    PaymentResult getByReference(String reference);
}
