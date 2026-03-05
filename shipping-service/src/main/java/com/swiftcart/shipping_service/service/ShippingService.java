package com.swiftcart.shipping_service.service;

import com.swiftcart.shipping_service.client.NotificationClient;
import com.swiftcart.shipping_service.client.NotificationRequest;
import com.swiftcart.shipping_service.client.NotificationType;
import com.swiftcart.shipping_service.client.UserClient;
import com.swiftcart.shipping_service.client.UserResponse;
import com.swiftcart.shipping_service.dtos.ShippingRequest;
import com.swiftcart.shipping_service.dtos.ShippingResponse;
import com.swiftcart.shipping_service.exceptions.ResourceNotFoundException;
import com.swiftcart.shipping_service.mapper.ShippingMapper;
import com.swiftcart.shipping_service.model.Shipping;
import com.swiftcart.shipping_service.model.ShippingStatus;
import com.swiftcart.shipping_service.repository.ShippingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShippingService {

    private final ShippingRepository repository;
    private final ShippingMapper mapper;

    private final NotificationClient notificationClient;
    private final UserClient userClient;

    public ShippingService(ShippingRepository repository, ShippingMapper mapper, NotificationClient notificationClient, UserClient userClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.notificationClient = notificationClient;
        this.userClient = userClient;
    }

    public ShippingResponse createShipping(ShippingRequest request) {
        Shipping shipping = mapper.toEntity(request);
        Shipping savedShipping = repository.save(shipping);

        // Create the response first so we have the tracking number!
        ShippingResponse response = mapper.toResponse(savedShipping);
        String trackingNumber = response.getTrackingNumber();

        try {

            UserResponse user = userClient.getUserById(request.getUserId());

            // Build the Shipping Notification
            NotificationRequest notification = new NotificationRequest();
            notification.setRecipientEmail(user.getEmail());
            notification.setSubject("SwiftCart: Your order has shipped!");
            notification.setMessage("Hi " + user.getUsername() + ",\nGood news! Your order is on the way.\nTracking Number: " + trackingNumber);
            notification.setType(NotificationType.SHIPPING_UPDATE);

            // Fire the email!
            notificationClient.sendNotification(notification);
            System.out.println("Shipping update email sent to: " + user.getEmail());

        } catch (Exception e) {
            System.out.println("Could not send shipping email: " + e.getMessage());
        }

        // 5. FIXED: The return statement must always be the very last line!
        return response;
    }

    public ShippingResponse getShippingByTrackingNumber(String trackingNumber) {
        Shipping shipping = repository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Shipping not found for tracking number: " + trackingNumber));
        return mapper.toResponse(shipping);
    }

    public ShippingResponse updateShippingStatus(String trackingNumber, ShippingStatus newStatus) {
        Shipping shipping = repository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Shipping not found for tracking number: " + trackingNumber));

        shipping.setStatus(newStatus);
        Shipping updatedShipping = repository.save(shipping);
        return mapper.toResponse(updatedShipping);
    }

    public List<ShippingResponse> getAllShippings() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}