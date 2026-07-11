package com.shopflow.orders.event;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {

    private String eventId;

    private Long orderId;

    private String customerName;

    private String productName;

    private Integer quantity;

    private BigDecimal price;

}