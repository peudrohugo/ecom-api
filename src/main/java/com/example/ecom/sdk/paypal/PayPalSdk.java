package com.example.ecom.sdk.paypal;

import java.math.BigDecimal;

public class PayPalSdk {
    public PayPalPayment createPayment(BigDecimal total) {
        PayPalPayment paypalPayment = getMockedPaymentObject("pay_" + System.currentTimeMillis(), total,"PENDING");
        paypalPayment.created_time = java.time.LocalDateTime.now();
        System.out.println("PayPal: Payment created: " + paypalPayment.id);
        return paypalPayment;
    }

    public PayPalPayment executePayment(String paymentId, double value) {
        PayPalPayment paypalPayment = getMockedPaymentObject(paymentId, BigDecimal.valueOf(value), "APPROVED");
        paypalPayment.updated_time = java.time.LocalDateTime.now();
        System.out.println("PayPal: Payment executed: " + paypalPayment.id);
        return paypalPayment;
    }

    public PayPalPayment getPaymentDetails(String paymentId) {
        System.out.println("PayPal: Retrieved payment details for: " + paymentId);
        return getMockedPaymentObject(paymentId, BigDecimal.valueOf(100.00), "APPROVED");
    }

    private PayPalPayment getMockedPaymentObject(String paymentId, BigDecimal total, String state) {
        PayPalPayment paypalPayment = new PayPalPayment();
        paypalPayment.id = paymentId;
        paypalPayment.state = state;
        paypalPayment.type = "SALE";
        paypalPayment.total = total;
        paypalPayment.currency = "BRL";
        return paypalPayment;
    }
}
