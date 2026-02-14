package com.swiftcart.review_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;   // ID of the product being reviewed
    private Long userId;      // ID of the user who wrote the review
    private String userName;  // Storing name to avoid fetching from User Service every time
    @Column(length = 1000)
    private String content;   // The actual review text
    private int rating;       // 1 to 5 stars
    private LocalDateTime createdAt;
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}