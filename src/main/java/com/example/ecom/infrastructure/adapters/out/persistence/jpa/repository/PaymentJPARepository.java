package com.example.ecom.infrastructure.adapters.out.persistence.jpa.repository;

import com.example.ecom.infrastructure.adapters.out.persistence.jpa.data.PaymentData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJPARepository extends JpaRepository<PaymentData, String> {}
