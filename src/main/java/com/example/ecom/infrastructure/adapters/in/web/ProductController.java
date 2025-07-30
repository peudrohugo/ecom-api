package com.example.ecom.infrastructure.adapters.in.web;

import com.example.ecom.application.inbounds.CreateProductData;
import com.example.ecom.application.outbounds.ProductDTO;
import com.example.ecom.domain.port.in.IProductService;
import com.example.ecom.infrastructure.shared.exceptions.DuplicateProductNameException;
import com.example.ecom.infrastructure.shared.exceptions.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final IProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody CreateProductData data) {
        try {
            ProductDTO createdProduct = productService.createProduct(data);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (DuplicateProductNameException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId)
                .map(productDto -> new ResponseEntity<>(productDto, HttpStatus.OK))
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found."));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProductsByName(@RequestParam String name) {
        List<ProductDTO> products = productService.searchProductsByName(name);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }}
