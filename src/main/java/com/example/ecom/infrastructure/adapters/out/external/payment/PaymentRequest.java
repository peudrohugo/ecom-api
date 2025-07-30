package com.example.ecom.infrastructure.adapters.out.external.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentRequest {
    private double amount;
    private String currency; //"BRL","USD","EUR"
    private String token;
}
