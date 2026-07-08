package com.shopflow.orders.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import com.shopflow.orders.dto.InventoryResponse;
import com.shopflow.orders.dto.ReserveProductRequest;
import org.springframework.web.client.HttpClientErrorException;

@Component
@RequiredArgsConstructor
public class InventoryClient {

    private final RestClient restClient;

    public InventoryResponse reserveProduct(
            String productCode,
            Integer quantity) {

        ReserveProductRequest request =
                new ReserveProductRequest(quantity);
        try {
            return restClient.post()
                    .uri("http://localhost:8082/api/inventory/products/{code}/reserve",
                            productCode)
                    .body(request)
                    .retrieve()
                    .body(InventoryResponse.class);
        } catch (HttpClientErrorException ex) {
            throw ex;
        }
    }

}