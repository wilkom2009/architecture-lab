package com.shopflow.payments.repository;

import com.shopflow.payments.model.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProcessedEventRepository
        extends JpaRepository<ProcessedEvent, String> {
}