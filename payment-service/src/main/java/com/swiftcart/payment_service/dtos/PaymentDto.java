package com.swiftcart.payment_service.dtos;

import com.swiftcart.payment_service.model.PaymentStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentDto {
    private String orderId;
    private String stripePaymentIntentId;
    private Long amount;
    private String currency;
    private PaymentStatus status;
    private LocalDateTime createdAt;
}