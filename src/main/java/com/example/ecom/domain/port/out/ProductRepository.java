package com.example.ecom.domain.port.out;

import com.example.ecom.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findByName(String name);

    List<Product> findAll();

    void delete(Long id);

    boolean existsById(Long id);

    Product incrementStock(Long productId, int quantity);

    Product decrementStock(Long productId, int quantity);
}
