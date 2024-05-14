package com.fer.hr.review.service.impl;

import com.fer.hr.review.model.AverageRatingAndCount;
import com.fer.hr.review.model.Review;
import com.fer.hr.review.repository.ReviewRepository;
import com.fer.hr.review.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    public final ReviewRepository reviewRepository;
    @Override
    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public Review addReview(Review review) {
        reviewRepository.saveAndFlush(review);

        return review;
    }

    @Override
    public AverageRatingAndCount getAverageReviewsRatingAndCountByProductId(Long productId) {
        List<Review> reviewList = getReviewsByProductId(productId);

        int count = reviewList.size();
        double averageRating = reviewList.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0);

        AverageRatingAndCount averageRatingAndCount = AverageRatingAndCount
                .builder()
                .averageRating(averageRating)
                .count(count)
                .build();

        return averageRatingAndCount;
    }
}
