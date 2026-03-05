package com.swiftcart.review_service.mapper;

import com.swiftcart.review_service.dtos.ReviewRequest;
import com.swiftcart.review_service.dtos.ReviewResponse;
import com.swiftcart.review_service.model.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public Review toEntity(ReviewRequest request) {
        Review review = new Review();
        review.setProductId(request.getProductId());
        review.setUserId(request.getUserId());
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        return review;
    }

    public ReviewResponse toDto(Review review, String reviewerName) {
        ReviewResponse response = new ReviewResponse();
        response.setId(review.getId());
        response.setProductId(review.getProductId());
        response.setUserId(review.getUserId());
        response.setRating(review.getRating());
        response.setComment(review.getComment());
        response.setVerifiedBuyer(review.isVerifiedBuyer());
        response.setCreatedAt(review.getCreatedAt());
        // Inject the name fetched from Identity Service
        response.setReviewerName(reviewerName);
        return response;
    }
}