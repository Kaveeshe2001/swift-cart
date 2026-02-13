package com.swiftcart.product_service.dtos;

import java.util.List;

public record ProductResponse (
        Long id,
        String brand,
        String modelName,
        String referenceNumber,
        String movementType,
        Integer caseSizeMm,
        String caseMaterial,
        String strapMaterial,
        Double price,
        List<String> imageUrls
) { }
