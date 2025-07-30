package com.example.ecom.infrastructure.config;

import com.example.ecom.domain.port.out.OrderRepository;
import com.example.ecom.domain.port.out.PaymentPort;
import com.example.ecom.domain.port.out.ProductRepository;
import com.example.ecom.domain.port.out.UserRepository;
import com.example.ecom.domain.service.OrderService;
import com.example.ecom.domain.service.ProductService;
import com.example.ecom.domain.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.example.ecom.application.service",
        "com.example.ecom.infrastructure.adapters"
})
public class AppConfig {
    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }

    @Bean
    public ProductService productService(ProductRepository productRepository, OrderRepository orderRepository) {
        return new ProductService(productRepository, orderRepository);
    }

    @Bean
    public OrderService orderProcessingService(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            PaymentPort paymentPort) {
        return new OrderService(orderRepository, productRepository, paymentPort);
    }
}
