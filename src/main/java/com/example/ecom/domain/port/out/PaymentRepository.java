package com.example.ecom.domain.port.out;

import com.example.ecom.domain.model.Payment;

import java.util.Optional;

public interface PaymentRepository {
    Payment save(Payment payment);

    Optional<Payment> findById(String id);
}
