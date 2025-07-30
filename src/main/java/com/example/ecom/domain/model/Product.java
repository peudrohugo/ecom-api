package com.example.ecom.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;

    @Builder
    private Product(Long id, String name, String description, BigDecimal price, int stockQuantity) {
        validateProductData(id, name, description, price, stockQuantity);

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public static Product createNewProduct(String name, String description, BigDecimal price, int stockQuantity) {
        return Product.builder()
                .id(null)
                .name(name)
                .description(description)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
    }

    private void validateProductData(Long id, String name, String description, BigDecimal price, int stockQuantity) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or empty.");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price cannot be null or negative.");
        }
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative.");
        }
    }

    public void decrementStock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Decrement quantity cannot be negative.");
        }
        if (this.stockQuantity < quantity) {
            throw new IllegalArgumentException("Insufficient stock for product " + name + ". Available: " + this.stockQuantity + ", Requested: " + quantity);
        }
        this.stockQuantity -= quantity;
    }

    public void incrementStock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Increment quantity cannot be negative.");
        }
        this.stockQuantity += quantity;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price cannot be null or negative.");
        }
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative.");
        }
        this.stockQuantity = stockQuantity;
    }
}
