package com.fitmesh.fitmeshbackend.application.ports.out;

import com.fitmesh.fitmeshbackend.domain.valueobject.PaymentReference;

public interface PaymentReferenceGenerator {

    PaymentReference generateNext();
}
