package com.shopflow.inventory.dto;

import jakarta.validation.constraints.Positive;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReserveProductRequest {

    @Positive(message = "Quantity must be greater than 0")
    private Integer quantity;

}