package com.example.ecom.application.inbounds;

import java.util.List;

public record PlaceOrderData(
        Long userId,
        List<OrderItemData> items
) {
    public record OrderItemData(
            Long productId,
            int quantity
    ) {}
}
