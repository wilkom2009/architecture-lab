package com.shopflow.inventory.controller;

import com.shopflow.inventory.model.ProductInventory;
import com.shopflow.inventory.service.InventoryService;
import org.springframework.web.bind.annotation.RequestBody;
import com.shopflow.inventory.dto.CreateProductRequest;
import com.shopflow.inventory.dto.UpdateQuantityRequest;
import com.shopflow.inventory.dto.ReserveProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/products")
    public ProductInventory createProduct(
            @Valid @RequestBody CreateProductRequest request) {

        return inventoryService.createProduct(
                request);
    }

    @GetMapping("/products/{productCode}")
    public ProductInventory getProduct(
        @PathVariable String productCode) {

        return inventoryService.getProduct(productCode);
    }

    @PutMapping("/products/{productCode}")
    public ProductInventory updateQuantity(
        @PathVariable String productCode,
        @Valid @RequestBody UpdateQuantityRequest request) {
    
        return inventoryService.updateQuantity(productCode, request);
    }

    @DeleteMapping("/products/{productCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String productCode) {
        
        inventoryService.deleteProduct(productCode);
    }

    @PostMapping("/products/{productCode}/reserve")
    public ProductInventory reserveProduct(
        @PathVariable String productCode,
        @Valid @RequestBody ReserveProductRequest request) {

        return inventoryService.reserveProduct(productCode, request);
    }

}