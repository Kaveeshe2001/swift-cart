package com.swiftcart.cart_service.mapper;

import com.swiftcart.cart_service.dtos.CartItemResponse;
import com.swiftcart.cart_service.dtos.CartResponse;
import com.swiftcart.cart_service.model.Cart;
import com.swiftcart.cart_service.model.CartItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartResponse toCartResponse(Cart cart) {
        CartResponse response = new CartResponse();
        response.setId(cart.getId());
        response.setUserId(cart.getUserId());
        response.setItems(cart.getItems().stream()
                .map(this::toCartItemResponse)
                .collect(Collectors.toList()));
        response.setTotalCartValue(cart.getTotalCartValue());
        return response;
    }

    public CartItemResponse toCartItemResponse(CartItem cartItem) {
        CartItemResponse response = new CartItemResponse();
        response.setId(cartItem.getId());
        response.setProductId(cartItem.getProductId());
        response.setProductName(cartItem.getProductName());
        response.setUnitPrice(cartItem.getUnitPrice());
        response.setQuantity(cartItem.getQuantity());
        response.setTotalPrice(cartItem.getTotalPrice());
        response.setSelectedStrap(cartItem.getSelectedStrap());
        return response;
    }
}
