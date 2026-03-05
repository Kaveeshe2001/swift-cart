package com.swiftcart.review_service.service;

import com.swiftcart.review_service.client.*;
import com.swiftcart.review_service.dtos.ReviewRequest;
import com.swiftcart.review_service.dtos.ReviewResponse;
import com.swiftcart.review_service.exceptions.ReviewNotFoundException;
import com.swiftcart.review_service.mapper.ReviewMapper;
import com.swiftcart.review_service.model.Review;
import com.swiftcart.review_service.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserClient userClient;
    private final OrderClient orderClient;
    private final NotificationClient notificationClient;

    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper,
                         UserClient userClient, OrderClient orderClient, NotificationClient notificationClient) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.userClient = userClient;
        this.orderClient = orderClient;
        this.notificationClient = notificationClient;
    }

    public ReviewResponse addReview(ReviewRequest request) {
        // Get user details
        UserResponse user = userClient.getUserById(request.getUserId());

        // Check if verified buyer
        boolean isVerified = false;
        try {
            if (request.getOrderId() != null) {
                OrderResponse order = orderClient.getOrderById(request.getOrderId());
                if (order != null && order.getUserId().equals(request.getUserId())) {
                    isVerified = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Order verification failed: " + e.getMessage());
        }

        // Save review
        Review review = reviewMapper.toEntity(request);
        review.setVerifiedBuyer(isVerified);
        Review savedReview = reviewRepository.save(review);

        // Send Thank You Email
        try {
            NotificationRequest email = new NotificationRequest();
            email.setRecipientEmail(user.getEmail());
            email.setSubject("Thanks for your review!");
            email.setMessage("Hi " + user.getUsername() + ",\nThank you for leaving a " + request.getRating() + "-star review!");
            email.setType(NotificationType.WELCOME_MESSAGE);
            notificationClient.sendNotification(email);
        } catch (Exception e) {
            System.out.println("Could not send review email: " + e.getMessage());
        }

        return reviewMapper.toDto(savedReview, user.getUsername());
    }

    public List<ReviewResponse> getReviewsForWatch(Long productId) {
        return reviewRepository.findByProductId(productId)
                .stream()
                .map(review -> {
                    // Fetch the name for each review (In a real app, cache this to save API calls)
                    String name = userClient.getUserById(review.getUserId()).getUsername();
                    return reviewMapper.toDto(review, name);
                })
                .collect(Collectors.toList());
    }

    public ReviewResponse updateReview(Long id, ReviewRequest request) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + id));

        existingReview.setComment(request.getComment());
        existingReview.setRating(request.getRating());

        Review updatedReview = reviewRepository.save(existingReview);
        String name = userClient.getUserById(updatedReview.getUserId()).getUsername();
        return reviewMapper.toDto(updatedReview, name);
    }

    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ReviewNotFoundException("Review not found with id: " + id);
        }
        reviewRepository.deleteById(id);
    }

    public List<ReviewResponse> getReviewsForUser(Long userId) {
        String name = userClient.getUserById(userId).getUsername();
        return reviewRepository.findByUserId(userId)
                .stream()
                .map(review -> reviewMapper.toDto(review, name))
                .collect(Collectors.toList());
    }
}