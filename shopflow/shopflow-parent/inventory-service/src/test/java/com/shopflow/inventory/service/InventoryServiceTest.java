package com.shopflow.inventory.service;

import com.shopflow.inventory.exception.InsufficientStockException;
import com.shopflow.inventory.model.ProductInventory;
import com.shopflow.inventory.repository.ProductInventoryRepository;
import com.shopflow.inventory.dto.ReserveProductRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private ProductInventoryRepository repository;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    void shouldReserveProductWhenStockIsAvailable() {

        ProductInventory product = new ProductInventory();
        product.setProductCode("MacBook Pro");
        product.setAvailableQuantity(10);
        product.setReservedQuantity(0);

        when(repository.findByProductCode("MacBook Pro"))
                .thenReturn(Optional.of(product));

        ReserveProductRequest request = new ReserveProductRequest();
        request.setQuantity(3);

        inventoryService.reserveProduct("MacBook Pro", request);

        assertEquals(7, product.getAvailableQuantity());
        assertEquals(3, product.getReservedQuantity());

        verify(repository).save(product);
    }

    @Test
    void shouldThrowInsufficientStockExceptionWhenStockIsInsufficient() {

        ProductInventory product = new ProductInventory();
        product.setProductCode("MacBook Pro");
        product.setAvailableQuantity(2);
        product.setReservedQuantity(0);

        when(repository.findByProductCode("MacBook Pro"))
            .thenReturn(Optional.of(product));

        ReserveProductRequest request = new ReserveProductRequest();
        request.setQuantity(5);

        assertThrows(
            InsufficientStockException.class,
            () -> inventoryService.reserveProduct("MacBook Pro", request)
        );
    }
}