package com.fitmesh.fitmeshbackend.application.ports.in;

import com.fitmesh.fitmeshbackend.application.ports.in.result.AdminPaymentResult;

import java.util.List;

public interface GetAllPaymentsUseCase {

    List<AdminPaymentResult> getAllPayments();
}