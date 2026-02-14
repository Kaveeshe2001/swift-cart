package com.swiftcart.review_service.repository;

import com.swiftcart.review_service.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Custom method to find all reviews for a specific watch
    List<Review> findByProductId(Long productId);
}