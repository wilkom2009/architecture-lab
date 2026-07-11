package com.shopflow.orders.service;

import java.util.UUID;
import com.shopflow.orders.dto.OrderRequest;
import com.shopflow.orders.dto.OrderResponse;
import com.shopflow.orders.model.Order;
import com.shopflow.orders.mapper.OrderMapper;
import com.shopflow.orders.client.InventoryClient;
import com.shopflow.orders.repository.OrderRepository;
import com.shopflow.orders.exception.ResourceNotFoundException;
import com.shopflow.orders.producer.OrderEventProducer;
import com.shopflow.orders.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final InventoryClient inventoryClient;
    private final OrderEventProducer orderEventProducer;

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public OrderResponse findById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        return mapper.toResponse(order);
    }

    public OrderResponse save(OrderRequest request) {

        inventoryClient.reserveProduct(
            request.getProductName(),
            request.getQuantity());

        Order entity = mapper.toEntity(request);
        Order saved = repository.save(entity);
        
        OrderCreatedEvent event = new OrderCreatedEvent(
            UUID.randomUUID().toString(),
            saved.getId(),
            saved.getCustomerName(),
            saved.getProductName(),
            saved.getQuantity(),
            saved.getPrice()
        );
        orderEventProducer.publish(event);

        return mapper.toResponse(saved);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}