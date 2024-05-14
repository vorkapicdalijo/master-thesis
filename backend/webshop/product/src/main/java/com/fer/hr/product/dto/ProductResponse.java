package com.fer.hr.product.dto;

import com.fer.hr.clients.review.dto.AverageRatingAndCount;
import com.fer.hr.clients.review.dto.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Float price;
    private Float size;
    private Long categoryId;
    private Long typeId;
    private Long brandId;

    private AverageRatingAndCount averageRatingAndCount;
    private List<Review> reviews;

    private Integer amount;
}
