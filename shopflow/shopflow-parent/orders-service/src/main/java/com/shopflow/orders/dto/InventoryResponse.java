package com.shopflow.orders.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InventoryResponse {

    private Long id;

    private String productCode;

    private Integer availableQuantity;

    private Integer reservedQuantity;

}