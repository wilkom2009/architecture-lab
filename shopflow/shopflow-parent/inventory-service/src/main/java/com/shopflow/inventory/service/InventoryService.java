package com.shopflow.inventory.service;

import com.shopflow.inventory.model.ProductInventory;
import com.shopflow.inventory.repository.ProductInventoryRepository;
import com.shopflow.inventory.dto.CreateProductRequest;
import com.shopflow.inventory.dto.UpdateQuantityRequest;
import com.shopflow.inventory.dto.ReserveProductRequest;
import com.shopflow.inventory.exception.ProductNotFoundException;
import com.shopflow.inventory.exception.InsufficientStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ProductInventoryRepository repository;

    public ProductInventory createProduct(CreateProductRequest request) {
        ProductInventory inventory = new ProductInventory();

        inventory.setProductCode(request.getProductCode());
        inventory.setAvailableQuantity(request.getQuantity());
        inventory.setReservedQuantity(0);

        return repository.save(inventory);
    }

    public ProductInventory getProduct(String productCode) {
        
        return repository.findByProductCode(productCode)
            .orElseThrow(() ->
                    new ProductNotFoundException(productCode));
    }

    public ProductInventory updateQuantity(
        String productCode,
        UpdateQuantityRequest request) {
        ProductInventory inventory = repository.findByProductCode(productCode)
            .orElseThrow(() ->
                    new ProductNotFoundException(productCode));
        inventory.setAvailableQuantity(request.getQuantity());
        
        return repository.save(inventory);
    }

    public void deleteProduct(String productCode) {
        
        ProductInventory inventory = repository.findByProductCode(productCode)
            .orElseThrow(() ->
                    new ProductNotFoundException(productCode));
                    
        repository.delete(inventory);
    }

    public ProductInventory reserveProduct(
        String productCode,
        ReserveProductRequest request) {

        ProductInventory product = repository.findByProductCode(productCode)
            .orElseThrow(() ->
                    new ProductNotFoundException(
                        productCode));
        if (product.getAvailableQuantity() < request.getQuantity()) {
            throw new InsufficientStockException(
                "Insufficient stock for product : " + productCode);
        }
        product.setAvailableQuantity(
            product.getAvailableQuantity() - request.getQuantity());
        product.setReservedQuantity(
            product.getReservedQuantity() + request.getQuantity());

        return repository.save(product);
    }
}