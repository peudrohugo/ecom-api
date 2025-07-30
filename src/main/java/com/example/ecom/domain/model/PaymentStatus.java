package com.example.ecom.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {
    PENDING("Pending Confirmation"),
    APPROVED("Payment Approved"),
    FAILED("Payment Failed"),
    REFUNDED("Payment Refunded"),
    AUTHORIZED("Payment Authorized"),
    RECEIVED("Payment Received"),
    CANCELED("Payment Canceled"),
    UNKNOWN("Unknown Status");

    private final String displayName;
}
