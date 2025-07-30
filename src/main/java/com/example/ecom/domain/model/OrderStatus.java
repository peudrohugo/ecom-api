package com.example.ecom.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    PENDING("Pending Confirmation"),
    PROCESSING("In Progress"),
    SHIPPED("Shipped to Customer"),
    DELIVERED("Delivered to Customer"),
    COMPLETED("Completed Order"),
    CANCELLED("Cancelled by User/System"),
    REFUNDED("Payment Refunded");

    private final String displayName;
}
