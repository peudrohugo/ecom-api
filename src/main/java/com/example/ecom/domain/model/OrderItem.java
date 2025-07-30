package com.example.ecom.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItem {
    private Long id;
    private Long productId;
    private int quantity;
    private BigDecimal priceAtOrder;

    @Builder
    private OrderItem(Long id, Long productId, int quantity, BigDecimal priceAtOrder) {
        validateOrderItemData(id, productId, quantity, priceAtOrder);

        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtOrder = priceAtOrder;
    }

    private void validateOrderItemData(Long id, Long productId, int quantity, BigDecimal priceAtOrder) {
        if (id != null && id <= 0) {
            throw new IllegalArgumentException("Order Item ID must be positive if provided.");
        }
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Product ID for order item cannot be null or non-positive.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        if (priceAtOrder == null || priceAtOrder.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price at order cannot be null or negative.");
        }
    }

    public static OrderItem createNewOrderItem(Long productId, int quantity, BigDecimal priceAtOrder) {
        return OrderItem.builder()
                .id(null)
                .productId(productId)
                .quantity(quantity)
                .priceAtOrder(priceAtOrder)
                .build();
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        this.quantity = quantity;
    }

    public void setPriceAtOrder(BigDecimal priceAtOrder) {
        if (priceAtOrder == null || priceAtOrder.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price at order cannot be null or negative.");
        }
        this.priceAtOrder = priceAtOrder;
    }
}
