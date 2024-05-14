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

    @GetMapping()
    public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable Long productId) {
        List<Review> reviewList = reviewService.getReviewsByProductId(productId);

        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }

    @GetMapping("/average")
    public ResponseEntity<AverageRatingAndCount> getAverageReviewsRatingAndCountByProductId(@PathVariable Long productId) {
        AverageRatingAndCount averageRatingAndCount = reviewService.getAverageReviewsRatingAndCountByProductId(productId);

        return new ResponseEntity<>(averageRatingAndCount, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Review> addReview(@RequestBody Review reviewToAdd) {
        Review newReview = reviewService.addReview(reviewToAdd);

        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }

}
