package com.fer.hr.review.controller;

import com.fer.hr.review.model.AverageRatingAndCount;
import com.fer.hr.review.model.Review;
import com.fer.hr.review.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable("productId") Long productId) {
        List<Review> reviewList = reviewService.getReviewsByProductId(productId);

        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }
    @GetMapping("/{productId}/user/{userId}")
    public ResponseEntity<Review> getReviewByProductIdAndUserId(@PathVariable("productId") Long productId, @PathVariable("userId") String userId) {
        Review review = reviewService.getReviewByProductIdAndUserId(productId, userId);

        return new ResponseEntity<>(review, HttpStatus.OK);
    }



    @GetMapping("/average/{productId}")
    public ResponseEntity<AverageRatingAndCount> getAverageReviewsRatingAndCountByProductId(@PathVariable("productId") Long productId) {
        AverageRatingAndCount averageRatingAndCount = reviewService.getAverageReviewsRatingAndCountByProductId(productId);

        return new ResponseEntity<>(averageRatingAndCount, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Review> addReview(@RequestBody Review reviewToAdd) {
        Review newReview = reviewService.addReview(reviewToAdd);

        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }

}
