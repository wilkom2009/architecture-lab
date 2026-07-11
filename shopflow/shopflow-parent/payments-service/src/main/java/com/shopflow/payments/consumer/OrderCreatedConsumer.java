package com.shopflow.payments.consumer;

import com.shopflow.payments.event.OrderCreatedEvent;
import com.shopflow.payments.service.PaymentService;
import com.shopflow.payments.service.ProcessedEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedConsumer {

    private final PaymentService paymentService;
    private final ProcessedEventService processedEventService;

    @KafkaListener(
            topics = "order-created",
            groupId = "payments-group")
    public void consume(OrderCreatedEvent event) {
        log.info("Order received : {} - event : {}", 
            event.getOrderId(), 
            event.getEventId());

        // kafaka at-least-once delivery mecanism / indopotency instructions follow (27-33)
        if (processedEventService.alreadyProcessed(event.getEventId())) {
            return;
        }

        paymentService.processPayment(event);
        processedEventService.markAsProcessed(event.getEventId());
    }

}