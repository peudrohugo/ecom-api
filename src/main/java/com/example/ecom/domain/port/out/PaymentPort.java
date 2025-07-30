package com.example.ecom.domain.port.out;

import com.example.ecom.domain.model.PaymentStatus;
import com.example.ecom.infrastructure.adapters.out.external.payment.PaymentRequest;
import com.example.ecom.infrastructure.adapters.out.external.payment.PaymentResponse;

public interface PaymentPort {
    PaymentResponse createPayment(PaymentRequest request);

    PaymentResponse processPayment(PaymentRequest request);

    PaymentStatus getPaymentStatus(String transactionId);
}
