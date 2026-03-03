package com.swiftcart.shipping_service.exceptions;

public class ResourceNotfoundException extends RuntimeException {
    public ResourceNotfoundException(String message) {
        super(message);
    }
}
