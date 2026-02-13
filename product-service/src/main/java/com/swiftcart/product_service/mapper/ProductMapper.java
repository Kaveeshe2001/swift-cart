package com.swiftcart.product_service.mapper;

import com.swiftcart.product_service.dtos.ProductRequest;
import com.swiftcart.product_service.dtos.ProductResponse;
import com.swiftcart.product_service.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    // Convert DTO to Entity
    public Product toEntity(ProductRequest request) {
        if (request == null) {
            return null;
        }

        Product product = new Product();
        product.setBrand(request.brand());
        product.setModelName(request.modelName());
        product.setReferenceNumber(request.referenceNumber());
        product.setMovementType(request.movementType());
        product.setCaseSizeMm(request.caseSizeMm());
        product.setCaseMaterial(request.caseMaterial());
        product.setStrapMaterial(request.strapMaterial());
        product.setPrice(request.price());
        product.setImageUrls(request.imageUrls());

        return product;
    }

    // Convert Entity to DTO
    public ProductResponse toResponse(Product product) {
        if (product == null) {
            return null;
        }

        return new ProductResponse(
                product.getId(),
                product.getBrand(),
                product.getModelName(),
                product.getReferenceNumber(),
                product.getMovementType(),
                product.getCaseSizeMm(),
                product.getCaseMaterial(),
                product.getStrapMaterial(),
                product.getPrice(),
                product.getImageUrls()
        );
    }

    // Update an Existing entity with new data
    public void updateEntity(ProductRequest request, Product product) {
        if (request == null || product == null) {
            return;
        }

        product.setBrand(request.brand());
        product.setModelName(request.modelName());
        product.setReferenceNumber(request.referenceNumber());
        product.setMovementType(request.movementType());
        product.setCaseSizeMm(request.caseSizeMm());
        product.setCaseMaterial(request.caseMaterial());
        product.setStrapMaterial(request.strapMaterial());
        product.setPrice(request.price());

        // For lists, it's often safer to clear and addAll to keep the same collection instance
        if (request.imageUrls() != null) {
            product.getImageUrls().clear();
            product.getImageUrls().addAll(request.imageUrls());
        }
    }
}
