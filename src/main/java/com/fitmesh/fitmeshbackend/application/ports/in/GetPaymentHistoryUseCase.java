package com.fitmesh.fitmeshbackend.application.ports.in;

import com.fitmesh.fitmeshbackend.application.ports.in.result.PaymentHistoryResult;

import java.util.List;

public interface GetPaymentHistoryUseCase {

    List<PaymentHistoryResult> getPaymentHistoryForUser(String userId);

    List<PaymentHistoryResult> getPaymentHistoryForMembership(String userId, String membershipId);
}
