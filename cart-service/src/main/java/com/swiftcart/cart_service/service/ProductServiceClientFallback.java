package com.swiftcart.cart_service.service;

import com.swiftcart.cart_service.dtos.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class ProductServiceClientFallback implements ProductServiceClient {

    @Override
    public ProductDTO getProductById(Long id) {
        log.warn("Product service unavailable, using fallback for product ID: {}", id);
        
        // Return mock product for testing
        ProductDTO mockProduct = new ProductDTO();
        mockProduct.setId(id);
        mockProduct.setName("Mock Product " + id);
        mockProduct.setPrice(BigDecimal.valueOf(99.99));
        mockProduct.setDescription("This is a mock product for testing");
        mockProduct.setStockQuantity(100);
        mockProduct.setCategory("Test Category");
        
        return mockProduct;
    }
}
