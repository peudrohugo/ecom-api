package com.example.ecom.domain.service;

import com.example.ecom.domain.model.Product;
import com.example.ecom.domain.port.out.OrderRepository;
import com.example.ecom.domain.port.out.ProductRepository;
import com.example.ecom.infrastructure.shared.exceptions.DuplicateProductNameException;
import com.example.ecom.infrastructure.shared.exceptions.ProductNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public Product createNewProduct(Product product) {
        if (productRepository.findByName(product.getName()).stream()
                .anyMatch(p -> p.getName().equalsIgnoreCase(product.getName()))) {
            throw new DuplicateProductNameException("Product with name '" + product.getName() + "' already exists.");
        }


        return productRepository.save(product);
    }

    public Product updateProductDetails(Product product) {
        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + product.getId() + " not found."));

        if (!existingProduct.getName().equalsIgnoreCase(product.getName())) {
            if (productRepository.findByName(product.getName()).stream()
                    .anyMatch(p -> p.getName().equalsIgnoreCase(product.getName()) && !p.getId().equals(product.getId()))) {
                throw new DuplicateProductNameException("Product name '" + product.getName() + "' is already taken by another product.");
            }
        }

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());

        return productRepository.save(existingProduct);
    }

    public Product adjustStock(Long productId, int quantityChange) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found."));

        if (quantityChange > 0) {
            product.incrementStock(quantityChange);
        } else if (quantityChange < 0) {
            product.decrementStock(Math.abs(quantityChange));
        }

        return productRepository.save(product);
    }

    public Optional<Product> findProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> searchProductsByName(String name) {
        return productRepository.findByName(name);
    }

    public void deleteProduct(Long productId) {
        Product productToDelete = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found for deletion."));

         if (orderRepository.existsByProductId(productId)) {
             throw new IllegalStateException("Cannot delete product with active orders.");
         }

        productRepository.delete(productToDelete.getId());
    }
}
