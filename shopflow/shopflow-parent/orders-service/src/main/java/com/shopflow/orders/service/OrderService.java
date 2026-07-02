package com.shopflow.orders.service;

import com.shopflow.orders.dto.OrderRequest;
import com.shopflow.orders.dto.OrderResponse;
import com.shopflow.orders.entity.Order;
import com.shopflow.orders.mapper.OrderMapper;
import com.shopflow.orders.repository.OrderRepository;
import com.shopflow.orders.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;

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

        Order entity = mapper.toEntity(request);

        Order saved = repository.save(entity);

        return mapper.toResponse(saved);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}