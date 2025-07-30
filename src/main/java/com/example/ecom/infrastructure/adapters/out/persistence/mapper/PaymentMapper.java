package com.example.ecom.infrastructure.adapters.out.persistence.mapper;

import com.example.ecom.application.outbounds.PaymentDTO;
import com.example.ecom.domain.model.Payment;
import com.example.ecom.infrastructure.adapters.out.persistence.jpa.data.PaymentData;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public Payment toPayment(PaymentData paymentData) {
        if (paymentData == null) {
            return null;
        }
        return Payment.builder()
                .id(paymentData.getId())
                .orderId(paymentData.getOrderId())
                .total(paymentData.getTotal())
                .confirmed(paymentData.isConfirmed())
                .cancelled(paymentData.isCancelled())
                .createdAt(paymentData.getCreatedAt())
                .build();
    }

    public PaymentData toPaymentData(Payment payment) {
        if (payment == null) {
            return null;
        }
        PaymentData paymentData = new PaymentData(
                payment.getId(),
                payment.getOrderId(),
                payment.getTotal(),
                payment.isConfirmed(),
                payment.isCancelled(),
                payment.getCreatedAt()
        );
        return paymentData;
    }

    public PaymentDTO toPaymentDto(Payment payment) {
        if (payment == null) {
            return null;
        }
        return new PaymentDTO(
                payment.getId(),
                payment.getOrderId(),
                payment.getTotal(),
                payment.isConfirmed(),
                payment.isCancelled(),
                payment.getCreatedAt()
        );
    }
}
