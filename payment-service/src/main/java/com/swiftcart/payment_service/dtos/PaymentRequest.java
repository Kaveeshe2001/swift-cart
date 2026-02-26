package com.swiftcart.payment_service.dtos;

import lombok.Data;

@Data
public class PaymentRequest {
    private String orderId;
    private Long amount; // in cents
    private String currency; // e.g., "usd"
    private String receiptEmail;
}
