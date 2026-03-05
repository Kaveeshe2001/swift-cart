package com.swiftcart.warranty_service.service;

import com.swiftcart.warranty_service.dtos.WarrantyRequest;
import com.swiftcart.warranty_service.dtos.WarrantyResponse;
import com.swiftcart.warranty_service.exceptions.WarrantyNotFoundException;
import com.swiftcart.warranty_service.mapper.WarrantyMapper;
import com.swiftcart.warranty_service.model.Warranty;
import com.swiftcart.warranty_service.repository.WarrantyRepository;
import org.springframework.stereotype.Service;

@Service
public class WarrantyService {

    private final WarrantyRepository repository;
    private final WarrantyMapper mapper;

    public WarrantyService(WarrantyRepository repository, WarrantyMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public WarrantyResponse registerWarranty(WarrantyRequest request) {
        Warranty warranty = mapper.toEntity(request);
        Warranty savedWarranty = repository.save(warranty);
        return mapper.toDto(savedWarranty);
    }

    public WarrantyResponse checkWarrantyStatus(Long productId) {
        Warranty warranty = repository.findByProductId(productId)
                .orElseThrow(() -> new WarrantyNotFoundException("No warranty found for product ID: " + productId));
        return mapper.toDto(warranty);
    }
}