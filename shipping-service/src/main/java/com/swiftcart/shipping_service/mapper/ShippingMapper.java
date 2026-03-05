package com.swiftcart.shipping_service.mapper;

import com.swiftcart.shipping_service.dtos.ShippingRequest;
import com.swiftcart.shipping_service.dtos.ShippingResponse;
import com.swiftcart.shipping_service.model.Shipping;
import com.swiftcart.shipping_service.model.ShippingStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ShippingMapper {

    public Shipping toEntity(ShippingRequest request) {
        Shipping shipping = new Shipping();
        shipping.setOrderId(request.getOrderId());
        shipping.setCarrier(request.getCarrier());
        shipping.setShippingAddress(request.getShippingAddress());
        shipping.setStatus(ShippingStatus.PENDING);
        shipping.setEstimatedDeliveryDate(LocalDate.now().plusDays(5)); // Default 5-day shipping
        return shipping;
    }

    public ShippingResponse toResponse(Shipping shipping) {
        // Using empty constructor and setters instead of all-arguments constructor
        ShippingResponse response = new ShippingResponse();

        response.setId(shipping.getId());
        response.setOrderId(shipping.getOrderId());
        response.setTrackingNumber(shipping.getTrackingNumber());
        response.setStatus(shipping.getStatus());
        response.setCarrier(shipping.getCarrier());
        response.setShippingAddress(shipping.getShippingAddress());
        response.setEstimatedDeliveryDate(shipping.getEstimatedDeliveryDate());

        return response;
    }

}
