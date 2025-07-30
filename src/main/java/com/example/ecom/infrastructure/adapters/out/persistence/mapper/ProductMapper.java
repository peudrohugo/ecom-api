package com.example.ecom.infrastructure.adapters.out.persistence.mapper;

import com.example.ecom.domain.model.Product;
import com.example.ecom.infrastructure.adapters.out.persistence.jpa.data.ProductData;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toProduct(ProductData productData) {
        if (productData == null) {
            return null;
        }
        return Product.builder()
                .id(productData.getId())
                .name(productData.getName())
                .description(productData.getDescription())
                .price(productData.getPrice())
                .stockQuantity(productData.getStockQuantity())
                .build();
    }

    public ProductData toProductData(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductData(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }
}
