package com.fer.hr.product.dto;

import com.fer.hr.clients.review.dto.AverageRatingAndCount;
import com.fer.hr.clients.review.dto.Review;
import com.fer.hr.product.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Long productId;

    private String name;
    private String description;
    private String imageUrl;

    private Category category;

    private Type type;

    private Brand brand;

    private List<SizePrice> sizePrices;

    private List<ProductNote> productNotes;

    private AverageRatingAndCount averageRatingAndCount;

    private List<Review> reviews;

    private Integer amount;

}
