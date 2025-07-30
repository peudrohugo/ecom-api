package com.example.ecom.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Payment {
    private String id;
    private Long orderId;
    private BigDecimal total;
    private boolean confirmed;
    private boolean cancelled;
    private LocalDateTime createdAt;

    @Builder
    private Payment(String id, Long orderId, BigDecimal total, boolean confirmed, boolean cancelled, LocalDateTime createdAt) {
        validatePaymentData(id, orderId, total, createdAt);

        this.id = id;
        this.orderId = orderId;
        this.total = total;
        this.confirmed = confirmed;
        this.cancelled = cancelled;
        this.createdAt = createdAt;
    }

    public static Payment createNewPayment(String id, Long orderId, BigDecimal total) {
        return Payment.builder()
                .id(id)
                .orderId(orderId)
                .total(total)
                .confirmed(false)
                .cancelled(false)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private void validatePaymentData(String id, Long orderId, BigDecimal total, LocalDateTime createdAt) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Payment ID must not be empty.");
        }
        if (orderId == null || orderId <= 0) {
            throw new IllegalArgumentException("Order ID for payment cannot be null or non-positive.");
        }
        if (total == null || total.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total amount cannot be null or negative.");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("Order date cannot be null.");
        }
    }

    public void confirm() {
        if (this.cancelled) {
            throw new IllegalStateException("Cannot confirm a cancelled payment.");
        }
        this.confirmed = true;
    }

    public void cancel() {
        if (this.confirmed) {
            throw new IllegalStateException("Cannot cancel a confirmed payment directly; initiate refund process.");
        }
        this.cancelled = true;
    }
}
