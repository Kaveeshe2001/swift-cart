package com.swiftcart.product_service.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;           // e.g., Rolex, Casio
    private String modelName;       // e.g., Submariner
    private String referenceNumber; // e.g., 126610LN
    private String movementType;    // Automatic, Quartz, Manual
    private Integer caseSizeMm;     // 40, 42
    private String caseMaterial;    // Stainless Steel, Gold
    private String strapMaterial;   // Leather, Rubber
    private Double price;

    @ElementCollection
    @CollectionTable(name="product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>();

    public Product() {}

    public Product(Long id, String brand, String strapMaterial, String caseMaterial, Integer caseSizeMm, String movementType, String referenceNumber, String modelName, Double price, List<String> imageUrls) {
        this.id = id;
        this.brand = brand;
        this.strapMaterial = strapMaterial;
        this.caseMaterial = caseMaterial;
        this.caseSizeMm = caseSizeMm;
        this.movementType = movementType;
        this.referenceNumber = referenceNumber;
        this.modelName = modelName;
        this.price = price;
        this.imageUrls = imageUrls;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public Integer getCaseSizeMm() {
        return caseSizeMm;
    }

    public void setCaseSizeMm(Integer caseSizeMm) {
        this.caseSizeMm = caseSizeMm;
    }

    public String getCaseMaterial() {
        return caseMaterial;
    }

    public void setCaseMaterial(String caseMaterial) {
        this.caseMaterial = caseMaterial;
    }

    public String getStrapMaterial() {
        return strapMaterial;
    }

    public void setStrapMaterial(String strapMaterial) {
        this.strapMaterial = strapMaterial;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}

