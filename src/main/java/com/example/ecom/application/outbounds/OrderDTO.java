package com.example.ecom.application.outbounds;

import com.example.ecom.domain.model.OrderStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderDTO(
        Long id,
        Long userId,
        LocalDateTime orderDate,
        OrderStatus status,
        BigDecimal total,
        List<OrderItemDTO> items
) {
    @Builder
    public record OrderItemDTO(
            Long productId,
            String productName,
            int quantity,
            BigDecimal priceAtOrder
    ) {}
}
