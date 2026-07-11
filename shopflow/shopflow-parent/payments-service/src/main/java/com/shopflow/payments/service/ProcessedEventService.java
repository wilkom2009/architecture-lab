package com.shopflow.payments.service;

import com.shopflow.payments.repository.ProcessedEventRepository;
import com.shopflow.payments.model.ProcessedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessedEventService {

    private final ProcessedEventRepository repository;

    public boolean alreadyProcessed(String eventId) {
        return repository.findById(eventId).isPresent();
    }

    public void markAsProcessed(String eventId) {
        repository.save(
            new ProcessedEvent(eventId)
            );
    }
}