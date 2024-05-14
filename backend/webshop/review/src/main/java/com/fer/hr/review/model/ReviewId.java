package com.fer.hr.review.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class ReviewId implements Serializable {
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "user_id")
    private String userId;
}
