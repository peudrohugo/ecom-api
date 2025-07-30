package com.example.ecom.infrastructure.shared.exceptions;

public class DuplicateProductNameException extends RuntimeException {
    public DuplicateProductNameException(String message) {
        super(message);
    }
}
