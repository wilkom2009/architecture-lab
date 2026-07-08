package com.shopflow.payments.consumer;

import com.shopflow.payments.event.OrderCreatedEvent;
import com.shopflow.payments.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedConsumer {

    private final PaymentService paymentService;

    @KafkaListener(
            topics = "order-created",
            groupId = "payments-group")
    public void consume(OrderCreatedEvent event) {
        log.info("Order received : {}", event.getOrderId());
        paymentService.processPayment(event);
    }

}