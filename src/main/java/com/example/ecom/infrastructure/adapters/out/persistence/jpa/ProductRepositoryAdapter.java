package com.example.ecom.infrastructure.adapters.out.persistence.jpa;

import com.example.ecom.domain.model.Product;
import com.example.ecom.domain.port.out.ProductRepository;
import com.example.ecom.infrastructure.adapters.out.persistence.jpa.data.ProductData;
import com.example.ecom.infrastructure.adapters.out.persistence.jpa.repository.ProductJPARepository;
import com.example.ecom.infrastructure.adapters.out.persistence.mapper.ProductMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductRepositoryAdapter implements ProductRepository {
    private final ProductJPARepository productRepository;
    private final ProductMapper productMapper;

    public ProductRepositoryAdapter(ProductJPARepository productRepository,
                                    ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Product save(Product product) {
        ProductData productData = productMapper.toProductData(product);
        ProductData savedProductData = productRepository.save(productData);
        return productMapper.toProduct(savedProductData);
    }

    @Override
    public Optional<Product> findById(Long id) {
        Optional<ProductData> productDataOptional = productRepository.findById(id);
        return productDataOptional.map(productMapper::toProduct);
    }

    @Override
    public List<Product> findByName(String name) {
        List<ProductData> productDataList = productRepository.findByNameContainingIgnoreCase(name);
        return productDataList.stream()
                .map(productMapper::toProduct)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findAll() {
        List<ProductData> allProductData = productRepository.findAll();
        return allProductData.stream()
                .map(productMapper::toProduct)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

    @Override
    @Transactional
    public Product decrementStock(Long productId, int quantity) {
        ProductData productData = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        if (productData.getStockQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock for product ID: " + productId);
        }

        productData.setStockQuantity(productData.getStockQuantity() - quantity);
        ProductData updatedProductData = productRepository.save(productData);
        return productMapper.toProduct(updatedProductData);
    }

    @Override
    @Transactional
    public Product incrementStock(Long productId, int quantity) {
        ProductData productData = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        productData.setStockQuantity(productData.getStockQuantity() + quantity);
        ProductData updatedProductData = productRepository.save(productData);
        return productMapper.toProduct(updatedProductData);
    }
}
