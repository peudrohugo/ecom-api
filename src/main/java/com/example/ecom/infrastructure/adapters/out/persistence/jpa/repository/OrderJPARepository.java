package com.example.ecom.infrastructure.adapters.out.persistence.jpa.repository;

import com.example.ecom.infrastructure.adapters.out.persistence.jpa.data.OrderData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderJPARepository extends JpaRepository<OrderData, Long> {
    List<OrderData> findByUserId(Long userId);

    List<OrderData> findByStatus(String status);

    List<OrderData> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
