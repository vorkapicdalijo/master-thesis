package com.fer.hr.product.dto;

import com.fer.hr.clients.review.dto.AverageRatingAndCount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Integer amount;
}
