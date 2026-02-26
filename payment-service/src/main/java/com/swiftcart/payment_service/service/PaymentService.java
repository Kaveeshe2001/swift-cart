package com.swiftcart.payment_service.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.swiftcart.payment_service.dtos.PaymentRequest;
import com.swiftcart.payment_service.dtos.PaymentResponseDto;
import com.swiftcart.payment_service.model.Payment;
import com.swiftcart.payment_service.model.PaymentStatus;
import com.swiftcart.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.swiftcart.payment_service.dtos.PaymentDto;
import com.swiftcart.payment_service.exceptions.PaymentNotFoundException;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentResponseDto createPaymentIntent(PaymentRequest request) throws StripeException {
        // 1. Ask Stripe to create a Payment Intent
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(request.getAmount())
                        .setCurrency(request.getCurrency())
                        .setReceiptEmail(request.getReceiptEmail())
                        .putMetadata("orderId", request.getOrderId()) // Good practice to send your order ID to Stripe
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        PaymentIntent intent = PaymentIntent.create(params);

        // 2. Save the PENDING payment to our local database
        Payment payment = Payment.builder()
                .stripePaymentIntentId(intent.getId())
                .orderId(request.getOrderId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status(PaymentStatus.PENDING)
                .build();

        paymentRepository.save(payment);

        // 3. Return the secret to the frontend
        return new PaymentResponseDto(intent.getClientSecret());
    }

    public PaymentDto getPaymentByOrderId(String orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found for order ID: " + orderId));

        // Convert Entity to DTO using the Builder
        return PaymentDto.builder()
                .orderId(payment.getOrderId())
                .stripePaymentIntentId(payment.getStripePaymentIntentId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .status(payment.getStatus())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}