package com.example.ecom.infrastructure.adapters.out.persistence.mapper;

import com.example.ecom.domain.model.Order;
import com.example.ecom.domain.model.OrderItem;
import com.example.ecom.domain.model.OrderStatus;
import com.example.ecom.infrastructure.adapters.out.persistence.jpa.data.OrderData;
import com.example.ecom.infrastructure.adapters.out.persistence.jpa.data.OrderItemData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public Order toOrder(OrderData orderData) {
        if (orderData == null) {
            return null;
        }
        List<OrderItem> orderItems = orderData.getItems().stream()
                .map(this::toOrderItem)
                .collect(Collectors.toList());

        return Order.builder()
                .id(orderData.getId())
                .userId(orderData.getUserId())
                .orderDate(orderData.getOrderDate())
                .status(OrderStatus.valueOf(orderData.getStatus().toString()))
                .totalAmount(orderData.getTotalAmount())
                .items(orderItems)
                .build();
    }

    public OrderData toOrderData(Order order) {
        if (order == null) {
            return null;
        }
        List<OrderItemData> orderItemData = order.getItems().stream()
                .map(this::toOrderItemData)
                .collect(Collectors.toList());

        return new OrderData(
                order.getId(),
                order.getUserId(),
                order.getOrderDate(),
                order.getStatus(),
                order.getTotalAmount(),
                orderItemData
        );
    }

    private OrderItem toOrderItem(OrderItemData orderItemData) {
        if (orderItemData == null) {
            return null;
        }
        return OrderItem.builder()
                .id(orderItemData.getId())
                .productId(orderItemData.getProductId())
                .quantity(orderItemData.getQuantity())
                .priceAtOrder(orderItemData.getPriceAtOrder())
                .build();
    }

    private OrderItemData toOrderItemData(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        return new OrderItemData(
                orderItem.getId(),
                orderItem.getProductId(),
                orderItem.getQuantity(),
                orderItem.getPriceAtOrder()
        );
    }
}
