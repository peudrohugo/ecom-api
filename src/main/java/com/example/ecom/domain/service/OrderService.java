package com.example.ecom.domain.service;

import com.example.ecom.domain.model.*;
import com.example.ecom.domain.port.out.OrderRepository;
import com.example.ecom.domain.port.out.PaymentPort;
import com.example.ecom.domain.port.out.ProductRepository;
import com.example.ecom.infrastructure.adapters.out.external.payment.PaymentRequest;
import com.example.ecom.infrastructure.adapters.out.external.payment.PaymentResponse;
import com.example.ecom.infrastructure.shared.exceptions.InsufficientStockException;
import com.example.ecom.infrastructure.shared.exceptions.OrderNotFoundException;
import com.example.ecom.infrastructure.shared.exceptions.PaymentException;
import com.example.ecom.infrastructure.shared.exceptions.ProductNotFoundException;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PaymentPort paymentPort;

    public Order placeOrder(Order order, PaymentRequest paymentDetails) {
        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product with ID " + item.getProductId() + " not found."));

            if (product.getStockQuantity() < item.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName() +
                        ". Available: " + product.getStockQuantity() +
                        ", Requested: " + item.getQuantity());
            }
        }

        PaymentResponse paymentResponse = paymentPort.processPayment(paymentDetails);
        if (paymentResponse.getStatus() != PaymentStatus.APPROVED && paymentResponse.getStatus() != PaymentStatus.AUTHORIZED) {
            throw new PaymentException("Payment failed for order " + order.getId() + ": " + paymentResponse.getErrorMessage());
        }

        for (OrderItem item : order.getItems()) {
            productRepository.decrementStock(item.getProductId(), item.getQuantity());
        }

        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(order.getTotalAmount());

        return orderRepository.save(order);
    }

    public Order confirmOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + orderId + " not found."));

        if (order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.PROCESSING) {
            throw new IllegalStateException("Order " + orderId + " cannot be confirmed in status: " + order.getStatus());
        }

        order.updateStatus(OrderStatus.PROCESSING);
        return orderRepository.save(order);
    }

    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + orderId + " not found."));

        if (order.getStatus() == OrderStatus.SHIPPED || order.getStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Order " + orderId + " cannot be cancelled as it's already " + order.getStatus());
        }

        for (OrderItem item : order.getItems()) {
            productRepository.incrementStock(item.getProductId(), item.getQuantity());
        }

        order.updateStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
}
