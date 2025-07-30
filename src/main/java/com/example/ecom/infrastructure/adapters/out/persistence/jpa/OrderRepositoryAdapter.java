package com.example.ecom.infrastructure.adapters.out.persistence.jpa;

import com.example.ecom.domain.model.Order;
import com.example.ecom.domain.model.OrderStatus;
import com.example.ecom.domain.port.out.OrderRepository;
import com.example.ecom.infrastructure.adapters.out.persistence.jpa.data.OrderData;
import com.example.ecom.infrastructure.adapters.out.persistence.jpa.repository.OrderJPARepository;
import com.example.ecom.infrastructure.adapters.out.persistence.mapper.OrderMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderRepositoryAdapter implements OrderRepository {
    private final OrderJPARepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderRepositoryAdapter(OrderJPARepository orderRepository,
                                     OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public Order save(Order order) {
        OrderData orderData = orderMapper.toOrderData(order);
        OrderData savedOrderData = orderRepository.save(orderData);
        return orderMapper.toOrder(savedOrderData);
    }

    @Override
    public Optional<Order> findById(Long id) {
        Optional<OrderData> orderDataOptional = orderRepository.findById(id);
        return orderDataOptional.map(orderMapper::toOrder);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        List<OrderData> orderDataList = orderRepository.findByUserId(userId);
        return orderDataList.stream()
                .map(orderMapper::toOrder)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        List<OrderData> orderDataList = orderRepository.findByStatus(status.name());
        return orderDataList.stream()
                .map(orderMapper::toOrder)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findOrderByDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        List<OrderData> orderDataList = orderRepository.findByOrderDateBetween(startDate, endDate);
        return orderDataList.stream()
                .map(orderMapper::toOrder)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return orderRepository.existsById(id);
    }

    @Override
    public boolean existsByProductId(Long productId) {
        return true;
    }
}
