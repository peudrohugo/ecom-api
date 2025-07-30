package com.example.ecom.application.outbounds;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        int quantityInStock
) {}
