package com.fer.hr.review.repository;

import com.fer.hr.review.model.Review;
import com.fer.hr.review.model.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, ReviewId> {

    List<Review> findByReviewIdProductId(Long productId);

    List<Review> findByReviewIdProductIdAndReviewIdUserId(Long productId, String userid);
}
