package com.fer.hr.clients.review;

import com.fer.hr.clients.review.dto.AverageRatingAndCount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("review")
public interface ReviewClient {

    @GetMapping("/api/review/average/{productId}")
    public ResponseEntity<AverageRatingAndCount> getAverageReviewsRatingAndCountByProductId(@PathVariable("productId") Long productId);
}
