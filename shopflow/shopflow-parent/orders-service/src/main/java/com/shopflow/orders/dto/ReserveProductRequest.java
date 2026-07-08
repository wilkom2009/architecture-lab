package com.shopflow.orders.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReserveProductRequest {

    @Positive(message = "Quantity must be greater than 0")
    private Integer quantity;

}