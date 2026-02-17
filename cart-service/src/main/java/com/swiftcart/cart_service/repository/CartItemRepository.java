package com.swiftcart.cart_service.repository;

import com.swiftcart.cart_service.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // No additional methods needed with unidirectional relationship
}
