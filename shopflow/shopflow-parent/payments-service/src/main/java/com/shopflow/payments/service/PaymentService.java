package com.shopflow.payments.service;

import com.shopflow.payments.event.OrderCreatedEvent;
import com.shopflow.payments.model.Payment;
import com.shopflow.payments.model.PaymentStatus;
import com.shopflow.payments.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Payment processPayment(OrderCreatedEvent event) {
        Payment payment = Payment.builder()
                .orderId(event.getOrderId())
                .amount(event.getPrice())
                .status(PaymentStatus.COMPLETED)
                .paymentDate(LocalDateTime.now())
                .build();

        Payment saved = paymentRepository.save(payment);
        log.info("Payment saved with id {}", saved.getId());
        return saved;

    }
}