package com.example.ecom.application.outbounds;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentDTO(
        String id,
        Long orderId,
        BigDecimal total,
        boolean confirmed,
        boolean cancelled,
        LocalDateTime createdAt
) {
}
