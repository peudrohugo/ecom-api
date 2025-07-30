package com.example.ecom.infrastructure.adapters.out.persistence.jpa.repository;

import com.example.ecom.infrastructure.adapters.out.persistence.jpa.data.ProductData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJPARepository extends JpaRepository<ProductData, Long> {
    List<ProductData> findByNameContainingIgnoreCase(String name);
}
