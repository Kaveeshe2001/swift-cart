package com.swiftcart.warranty_service.client;

import java.time.LocalDate;

public class OrderResponse {
    private Long id;
    private LocalDate orderDate;

    public OrderResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}
