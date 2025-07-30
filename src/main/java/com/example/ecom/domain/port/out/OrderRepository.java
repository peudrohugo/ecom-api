package com.example.ecom.domain.port.out;

import com.example.ecom.domain.model.Order;
import com.example.ecom.domain.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findByUserId(Long userId);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findOrderByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    void delete(Long id);

    boolean existsById(Long id);

    boolean existsByProductId(Long productId);
}
