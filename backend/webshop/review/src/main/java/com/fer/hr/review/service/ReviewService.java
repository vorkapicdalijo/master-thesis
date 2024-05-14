package com.fer.hr.review.service;

import com.fer.hr.review.model.AverageRatingAndCount;
import com.fer.hr.review.model.Review;

import java.util.List;

public interface ReviewService {

    public List<Review> getReviewsByProductId(Long productId);

    public Review addReview(Review review);
    public AverageRatingAndCount getAverageReviewsRatingAndCountByProductId(Long productId);
}
