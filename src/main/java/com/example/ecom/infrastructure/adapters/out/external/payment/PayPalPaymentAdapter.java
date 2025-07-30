package com.example.ecom.infrastructure.adapters.out.external.payment;

import com.example.ecom.domain.model.PaymentStatus;
import com.example.ecom.domain.port.out.PaymentPort;
import com.example.ecom.infrastructure.shared.exceptions.PaymentException;
import com.example.ecom.sdk.paypal.PayPalPayment;
import com.example.ecom.sdk.paypal.PayPalSdk;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PayPalPaymentAdapter implements PaymentPort {

    private final PayPalSdk payPalSdk;

    public PayPalPaymentAdapter() {
        this.payPalSdk = new PayPalSdk();
    }

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {
        try {
            PayPalPayment payPalPayment = payPalSdk.createPayment(BigDecimal.valueOf(request.getAmount()));

            return PaymentResponse.builder()
                    .transactionId(payPalPayment.id)
                    .status(PaymentStatus.valueOf(payPalPayment.state))
                    .errorMessage(null)
                    .build();
        } catch (RuntimeException e) {
            throw new PaymentException("PayPal payment creation failed: " + e.getMessage());
        }
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        try {
            PayPalPayment payPalPayment = payPalSdk.executePayment(request.getToken(), request.getAmount());

            return PaymentResponse.builder()
                    .transactionId(payPalPayment.id)
                    .status(PaymentStatus.valueOf(payPalPayment.state))
                    .errorMessage(null)
                    .build();
        } catch (RuntimeException e) {
            throw new PaymentException("PayPal payment failed: " + e.getMessage());
        }
    }

    @Override
    public PaymentStatus getPaymentStatus(String transactionId) {
        try {
            PayPalPayment payPalPayment = payPalSdk.getPaymentDetails(transactionId);
            System.out.println("The following PayPal payment is " + payPalPayment.state);
            return PaymentStatus.valueOf(payPalPayment.state);
        } catch (RuntimeException e) {
            throw new PaymentException("Failed to retrieve PayPal payment details: " + e.getMessage());
        }
    }
}
