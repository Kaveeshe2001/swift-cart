package com.swiftcart.product_service.controller;

import com.swiftcart.product_service.dtos.ProductRequest;
import com.swiftcart.product_service.dtos.ProductResponse;
import com.swiftcart.product_service.response.ApiResponse;
import com.swiftcart.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse createdProduct = productService.createProduct(productRequest);

        return new ResponseEntity<>(
                new ApiResponse("Product created successfully", createdProduct),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();

        return ResponseEntity.ok(
                new ApiResponse("All products retrieved successfully", products)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        ProductResponse product = productService.getProductById(id);

        return ResponseEntity.ok(
                new ApiResponse("Product found", product)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(
                new ApiResponse("Product deleted successfully", null)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        ProductResponse updatedProduct = productService.updateProduct(id, productRequest);

        return ResponseEntity.ok(
                new ApiResponse("Product updated successfully", updatedProduct)
        );
    }
}
