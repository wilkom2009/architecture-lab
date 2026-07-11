package com.shopflow.payments.service;

import com.shopflow.payments.model.ProcessedEvent;
import com.shopflow.payments.repository.ProcessedEventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProcessedEventServiceTest {

    @Mock
    private ProcessedEventRepository repository;

    @InjectMocks
    private ProcessedEventService processedEventService;

    @Test
    void shouldReturnTrueWhenEventAlreadyProcessed() {
        when(repository.findById("event-123"))
                .thenReturn(Optional.of(new ProcessedEvent("event-123")));
        boolean result =
                processedEventService.alreadyProcessed("event-123");
        assertTrue(result);
    }

    @Test
    void shouldSaveProcessedEvent() {
        processedEventService.markAsProcessed("event-123");
        verify(repository).save(new ProcessedEvent("event-123"));
    }

}