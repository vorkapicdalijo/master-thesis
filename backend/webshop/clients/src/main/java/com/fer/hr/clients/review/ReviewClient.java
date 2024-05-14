package com.fer.hr.clients.review;

import com.fer.hr.clients.review.dto.AverageRatingAndCount;
import com.fer.hr.clients.review.dto.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("review")
public interface ReviewClient {

    @GetMapping("/api/reviews/average/{productId}")
    public ResponseEntity<AverageRatingAndCount> getAverageReviewsRatingAndCountByProductId(@PathVariable("productId") Long productId);

    @GetMapping("/api/reviews/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable("productId") Long productId);
}
