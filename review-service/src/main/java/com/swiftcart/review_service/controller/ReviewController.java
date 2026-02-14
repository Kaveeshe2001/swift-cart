package com.swiftcart.review_service.controller;

import com.swiftcart.review_service.service.ReviewService;

import com.swiftcart.review_service.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews") // All endpoints start with /reviews
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 1. Add a review for a watch
    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewService.addReview(review);
    }

    // 2. Get all reviews for a specific watch
    @GetMapping("/product/{watchId}")
    public List<Review> getWatchReviews(@PathVariable Long watchId) {
        return reviewService.getReviewsForWatch(watchId);
    }
}