package com.example.ecom.application.service;

import com.example.ecom.application.inbounds.CreateProductData;
import com.example.ecom.application.outbounds.ProductDTO;
import com.example.ecom.domain.model.Product;
import com.example.ecom.domain.port.in.IProductService;
import com.example.ecom.domain.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductApplicationService implements IProductService {
    private final ProductService productService;

    @Override
    @Transactional
    public ProductDTO createProduct(CreateProductData data) {
        Product newProduct = Product.createNewProduct(data.name(), data.description(), BigDecimal.valueOf(data.price()), data.quantity());

        Product createdProduct = productService.createNewProduct(newProduct);
        return buildProductDTO(createdProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDTO> getProductById(Long productId) {
        Optional<Product> productOptional = productService.findProductById(productId);

        return Optional.of(buildProductDTO(productOptional.get()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productService.findAllProducts();

        return products.stream()
                .map(this::buildProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> searchProductsByName(String name) {
        List<Product> products = productService.searchProductsByName(name);

        return products.stream()
                .map(this::buildProductDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO buildProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantityInStock(product.getStockQuantity())
                .build();
    }
}
