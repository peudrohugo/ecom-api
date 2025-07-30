package com.example.ecom.infrastructure.shared.exceptions;

public class ExistingPaymentException extends RuntimeException {
    public ExistingPaymentException(String message) {
        super(message);
    }
}
