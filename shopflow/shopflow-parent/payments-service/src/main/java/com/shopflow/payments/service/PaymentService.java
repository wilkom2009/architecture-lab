package com.shopflow.payments.service;

import com.shopflow.payments.event.OrderCreatedEvent;
import com.shopflow.payments.model.Payment;
import com.shopflow.payments.model.PaymentStatus;
import com.shopflow.payments.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.math.BigDecimal; 

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Payment processPayment(OrderCreatedEvent event) {

        if (event.getPrice().compareTo(BigDecimal.valueOf(2000)) > 0) {
            log.error("Payment gateway unavailable");
            throw new RuntimeException("Payment gateway unavailable");
        }
        
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