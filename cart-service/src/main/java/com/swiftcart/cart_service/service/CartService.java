package com.swiftcart.cart_service.service;

import com.swiftcart.cart_service.dtos.AddToCartRequest;
import com.swiftcart.cart_service.dtos.CartResponse;
import com.swiftcart.cart_service.dtos.ProductDTO;
import com.swiftcart.cart_service.dtos.UpdateCartItemRequest;
import com.swiftcart.cart_service.exceptions.ResourceNotFoundException;
import com.swiftcart.cart_service.mapper.CartMapper;
import com.swiftcart.cart_service.model.Cart;
import com.swiftcart.cart_service.model.CartItem;
import com.swiftcart.cart_service.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final ProductServiceClient productServiceClient;
    private final CartMapper cartMapper;

    @Transactional(readOnly = true)
    public CartResponse getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "userId", userId));
        return cartMapper.toCartResponse(cart);
    }

    @Transactional
    public CartResponse addItemToCart(Long userId, AddToCartRequest request) {
        // Get or create cart for user
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    return cartRepository.save(newCart);
                });

        // Fetch product details from product service with manual fallback
        ProductDTO product;
        try {
            product = productServiceClient.getProductById(request.getProductId());
            log.info("Successfully fetched product from product service: {}", request.getProductId());
        } catch (Exception e) {
            log.warn("Product service unavailable, using mock product for ID: {}", request.getProductId(), e);
            // Manual fallback - create mock product
            product = new ProductDTO();
            product.setId(request.getProductId());
            product.setName("Mock Product " + request.getProductId());
            product.setPrice(BigDecimal.valueOf(99.99));
            product.setDescription("This is a mock product for testing");
            product.setStockQuantity(100);
            product.setCategory("Test Category");
        }

        // Check if product already exists in cart (with same customization if applicable)
        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(request.getProductId())
                        && (request.getSelectedStrap() == null || request.getSelectedStrap().equals(item.getSelectedStrap())))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            // Update quantity if item already exists
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            existingItem.calculateTotalPrice();
        } else {
            // Create new cart item
            CartItem newItem = new CartItem();
            newItem.setProductId(product.getId());
            newItem.setProductName(product.getName());
            newItem.setUnitPrice(product.getPrice());
            newItem.setQuantity(request.getQuantity());
            newItem.setSelectedStrap(request.getSelectedStrap());
            newItem.calculateTotalPrice();
            cart.getItems().add(newItem);
        }

        // Recalculate cart total
        cart.calculateTotalCartValue();
        cart = cartRepository.save(cart);

        return cartMapper.toCartResponse(cart);
    }

    @Transactional
    public CartResponse updateCartItem(Long userId, Long itemId, UpdateCartItemRequest request) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "userId", userId));

        // Reload cart to ensure items have IDs
        cart = cartRepository.findById(cart.getId()).orElseThrow();

        boolean itemFound = false;
        for (CartItem item : cart.getItems()) {
            if (item.getId() != null && item.getId().equals(itemId)) {
                item.setQuantity(request.getQuantity());
                item.calculateTotalPrice();
                itemFound = true;
                break;
            }
        }

        if (!itemFound) {
            throw new ResourceNotFoundException("CartItem", "id", itemId);
        }

        cart.calculateTotalCartValue();
        cart = cartRepository.save(cart);

        return cartMapper.toCartResponse(cart);
    }

    @Transactional
    public CartResponse removeItemFromCart(Long userId, Long itemId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "userId", userId));

        // Reload cart to ensure items have IDs
        cart = cartRepository.findById(cart.getId()).orElseThrow();

        CartItem itemToRemove = null;
        for (CartItem item : cart.getItems()) {
            if (item.getId() != null && item.getId().equals(itemId)) {
                itemToRemove = item;
                break;
            }
        }

        if (itemToRemove == null) {
            throw new ResourceNotFoundException("CartItem", "id", itemId);
        }

        cart.getItems().remove(itemToRemove);
        cart.calculateTotalCartValue();
        cart = cartRepository.save(cart);

        return cartMapper.toCartResponse(cart);
    }

    @Transactional
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "userId", userId));

        cart.getItems().clear();
        cart.calculateTotalCartValue();
        cartRepository.save(cart);
    }

    @Transactional
    public void deleteCart(Long userId) {
        cartRepository.deleteByUserId(userId);
    }
}
