package com.example.ecom.application.inbounds;

public record CreateProductData(
        String name,
        String description,
        double price,
        int quantity
) {}
