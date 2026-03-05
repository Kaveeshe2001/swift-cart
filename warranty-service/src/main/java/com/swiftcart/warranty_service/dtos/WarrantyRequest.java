package com.swiftcart.warranty_service.dtos;

import lombok.Data;

@Data
public class WarrantyRequest {
    private Long productId;
    private Long userId;
    private Long orderId;
    private int durationInMonths; // e.g., 12 or 24 months for a watch

    public WarrantyRequest() {
    }

    public int getDurationInMonths() {
        return durationInMonths;
    }

    public void setDurationInMonths(int durationInMonths) {
        this.durationInMonths = durationInMonths;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}