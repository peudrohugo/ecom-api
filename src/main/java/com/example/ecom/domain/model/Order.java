package com.example.ecom.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class Order {
    private Long id;
    private Long userId;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private List<OrderItem> items;

    @Builder
    private Order(Long id, Long userId, LocalDateTime orderDate, OrderStatus status, BigDecimal totalAmount, List<OrderItem> items) {
        validateOrderData(id, userId, orderDate, status, totalAmount, items);

        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.items = new ArrayList<>(items);
    }

    public static Order createNewOrder(Long userId, BigDecimal totalAmount, List<OrderItem> items) {
        return Order.builder()
                .id(null)
                .userId(userId)
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .totalAmount(totalAmount)
                .items(items)
                .build();
    }

    private void validateOrderData(Long id, Long userId, LocalDateTime orderDate, OrderStatus status, BigDecimal totalAmount, List<OrderItem> items) {
        if (id != null && id <= 0) {
            throw new IllegalArgumentException("Order ID must be positive if provided.");
        }
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID cannot be null or non-positive.");
        }
        if (orderDate == null) {
            throw new IllegalArgumentException("Order date cannot be null.");
        }
        if (status == null) {
            throw new IllegalArgumentException("Order status cannot be null.");
        }
        if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total amount cannot be null or negative.");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item.");
        }
    }

    public void updateStatus(OrderStatus newStatus) {
        if (this.status == OrderStatus.COMPLETED && newStatus == OrderStatus.PENDING) {
            throw new IllegalStateException("Cannot change order status from COMPLETED to PENDING.");
        }
        this.status = newStatus;
    }

    public void recalculateTotalAmount() {
        this.totalAmount = items.stream()
                .map(item -> item.getPriceAtOrder().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void setItems(List<OrderItem> items) {
        if (items == null) {
            this.items = new ArrayList<>();
        } else {
            this.items = new ArrayList<>(items);
        }
    }
}
