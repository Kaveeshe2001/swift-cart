package com.swiftcart.cart_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;        // Connects to Product Service ID
    private String productName;    // Snapshot: e.g. "Rolex Submariner"
    private Integer quantity;      // How many they want
    private BigDecimal unitPrice;  // Snapshot: Price at moment of adding
    private BigDecimal totalPrice; // Calculated: unitPrice * quantity

    // Optional: Watch specific customization
    private String selectedStrap;  // e.g. "Leather", "Metal"

    public void calculateTotalPrice() {
        this.totalPrice = this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
    }

    @PrePersist
    @PreUpdate
    public void preSave() {
        calculateTotalPrice();
    }
}
