package com.swiftcart.shipping_service.controller;

import com.swiftcart.shipping_service.dtos.ShippingRequest;
import com.swiftcart.shipping_service.dtos.ShippingResponse;
import com.swiftcart.shipping_service.model.ShippingStatus;
import com.swiftcart.shipping_service.response.ApiResponse;
import com.swiftcart.shipping_service.service.ShippingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/shipping")
public class ShippingController {
    private final ShippingService service;

    public ShippingController(ShippingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createShipping(@RequestBody ShippingRequest request) {
        ShippingResponse response = service.createShipping(request);
        return new ResponseEntity<>(new ApiResponse("Shipping created successfully", response), HttpStatus.CREATED);
    }

    @GetMapping("/{trackingNumber}")
    public ResponseEntity<ApiResponse> getShipping(@PathVariable String trackingNumber) {
        ShippingResponse response = service.getShippingByTrackingNumber(trackingNumber);
        return ResponseEntity.ok(new ApiResponse("Shipping retrieved successfully", response));
    }

    @PutMapping("/{trackingNumber}/status")
    public ResponseEntity<ApiResponse> updateStatus(
            @PathVariable String trackingNumber,
            @RequestParam ShippingStatus status) {
        ShippingResponse response = service.updateShippingStatus(trackingNumber, status);
        return ResponseEntity.ok(new ApiResponse("Shipping status updated", response));
    }
}
