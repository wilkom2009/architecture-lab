package com.shopflow.payments.consumer;

import com.shopflow.payments.event.OrderCreatedEvent;
import com.shopflow.payments.service.PaymentService;
import com.shopflow.payments.service.ProcessedEventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderCreatedConsumerTest {

    @Mock
    private PaymentService paymentService;

    @Mock
    private ProcessedEventService processedEventService;

    @InjectMocks
    private OrderCreatedConsumer consumer;

    @Test
    void shouldIgnoreAlreadyProcessedEvent() {

        OrderCreatedEvent event = new OrderCreatedEvent(
                "event-123",
                1L,
                "Wilson",
                "MacBook Pro",
                1,
                BigDecimal.valueOf(2500)
        );

        when(processedEventService.alreadyProcessed("event-123"))
                .thenReturn(true);

        consumer.consume(event);

        verify(paymentService, never()).processPayment(any());
        verify(processedEventService, never()).markAsProcessed(any());
    }

    @Test
    void shouldProcessNewEvent() {

        OrderCreatedEvent event = new OrderCreatedEvent(
                "event-123",
                1L,
                "Wilson",
                "MacBook Pro",
                1,
                BigDecimal.valueOf(2500)
        );

        when(processedEventService.alreadyProcessed("event-123"))
                .thenReturn(false);

        consumer.consume(event);

        verify(paymentService).processPayment(event);
        verify(processedEventService).markAsProcessed("event-123");
    }
}