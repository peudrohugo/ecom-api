package com.example.ecom.infrastructure.adapters.out.persistence.jpa.data;

import com.example.ecom.domain.model.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentData {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "confirmed", nullable = false)
    private boolean confirmed;

    @Column(name = "cancelled", nullable = false)
    private boolean cancelled;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
