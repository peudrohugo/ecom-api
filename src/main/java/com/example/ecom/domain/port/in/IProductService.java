package com.example.ecom.domain.port.in;

import com.example.ecom.application.inbounds.CreateProductData;
import com.example.ecom.application.outbounds.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    ProductDTO createProduct(CreateProductData data);

    Optional<ProductDTO> getProductById(Long productId);

    List<ProductDTO> getAllProducts();

    List<ProductDTO> searchProductsByName(String name);
}
