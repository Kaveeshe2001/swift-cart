package com.swiftcart.warranty_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service", path = "/inventory")
public interface InventoryClient {
    @GetMapping("/product/{id}")
    InventoryResponse getProductById(@PathVariable("id") Long id);
}