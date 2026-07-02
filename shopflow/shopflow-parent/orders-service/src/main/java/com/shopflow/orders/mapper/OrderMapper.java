package com.shopflow.orders.mapper;

import com.shopflow.orders.dto.OrderRequest;
import com.shopflow.orders.dto.OrderResponse;
import com.shopflow.orders.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Order toEntity(OrderRequest request);

    OrderResponse toResponse(Order order);
}