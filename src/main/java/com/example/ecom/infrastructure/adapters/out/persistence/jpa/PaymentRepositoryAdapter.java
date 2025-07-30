package com.example.ecom.infrastructure.adapters.out.persistence.jpa;

import com.example.ecom.domain.model.Payment;
import com.example.ecom.domain.port.out.PaymentRepository;
import com.example.ecom.infrastructure.adapters.out.persistence.jpa.data.PaymentData;
import com.example.ecom.infrastructure.adapters.out.persistence.jpa.repository.PaymentJPARepository;
import com.example.ecom.infrastructure.adapters.out.persistence.mapper.PaymentMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaymentRepositoryAdapter implements PaymentRepository {
    private final PaymentJPARepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentRepositoryAdapter(PaymentJPARepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public Payment save(Payment payment) {
        PaymentData paymentData = paymentMapper.toPaymentData(payment);
        PaymentData savedPaymentData = paymentRepository.save(paymentData);
        return paymentMapper.toPayment(savedPaymentData);
    }

    @Override
    public Optional<Payment> findById(String id) {
        Optional<PaymentData> paymentDataOptional = paymentRepository.findById(id);
        return paymentDataOptional.map(paymentMapper::toPayment);
    }
}
