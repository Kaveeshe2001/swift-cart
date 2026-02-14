package com.swiftcart.review_service.service;

import com.swiftcart.review_service.model.Review;
import com.swiftcart.review_service.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    // Save a new watch review
    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    // Get all reviews for a specific watch ID
    public List<Review> getReviewsForWatch(Long watchId) {
        return reviewRepository.findByProductId(watchId);
    }
}