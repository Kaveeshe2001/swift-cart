package com.swiftcart.product_service.service;

import com.swiftcart.product_service.dtos.ProductRequest;
import com.swiftcart.product_service.dtos.ProductResponse;
import com.swiftcart.product_service.exceptions.ProductNotFoundException;
import com.swiftcart.product_service.mapper.ProductMapper;
import com.swiftcart.product_service.model.Product;
import com.swiftcart.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    private ProductMapper productMapper;

    // constructor injection
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    // get all products
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)// Method reference for cleaner code
                .collect(Collectors.toList());
    }

    // create new product
    @PostMapping
    public ProductResponse createProduct(ProductRequest productRequest) {

        // Map DTO to Entity
        Product product = productMapper.toEntity(productRequest);

        // Save to DB
        Product savedProduct = productRepository.save(product);

        // Map Entity back to Response DTO
        return productMapper.toResponse(savedProduct);
    }

    // get product by id
    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toResponse)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    // delete product
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Cannot delete. Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
