package com.swiftcart.payment_service.controller;

import com.stripe.exception.StripeException;
import com.swiftcart.payment_service.dtos.PaymentDto;
import com.swiftcart.payment_service.dtos.PaymentRequest;
import com.swiftcart.payment_service.dtos.PaymentResponseDto;
import com.swiftcart.payment_service.response.ApiResponse;
import com.swiftcart.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-intent")
    public ResponseEntity<ApiResponse> createPaymentIntent(@RequestBody PaymentRequest request) throws StripeException {
        // StripeExceptions are pushed up to the GlobalExceptionHandler
        PaymentResponseDto responseData = paymentService.createPaymentIntent(request);
        return ResponseEntity.ok(new ApiResponse("Payment Intent created successfully", responseData));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> getPaymentByOrderId(@PathVariable String orderId) {
        PaymentDto paymentDto = paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity.ok(new ApiResponse("Payment retrieved successfully", paymentDto));
    }
}