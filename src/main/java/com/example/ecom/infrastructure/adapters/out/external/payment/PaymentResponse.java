package com.example.ecom.infrastructure.adapters.out.external.payment;

import com.example.ecom.domain.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PaymentResponse {
    private String transactionId;
    private PaymentStatus status;
    private String errorMessage;
}
