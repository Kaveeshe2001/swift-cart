package com.swiftcart.product_service.response;

import com.swiftcart.product_service.dtos.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

// Every single request returns the exact same format.
@AllArgsConstructor // generates a constructor that takes both arguments
@Data // automatically generates Getters, Setters, toString(), equals(), and hashCode()
public class ApiResponse {
    private String message; // holds a human-readable status message
    private Object data; // hold anything

    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
