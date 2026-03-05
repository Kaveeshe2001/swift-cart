package com.swiftcart.warranty_service.client;

public class InventoryResponse {
    private Long productId;
    private int warrantyMonths; // How long the warranty lasts

    public InventoryResponse() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    public void setWarrantyMonths(int warrantyMonths) {
        this.warrantyMonths = warrantyMonths;
    }
}
