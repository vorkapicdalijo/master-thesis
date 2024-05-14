package com.fer.hr.order.model;

import com.fer.hr.clients.review.dto.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Long id;
    private Long productId;
    private String name;
    private Double price;
    private Integer amount;
    private Long orderId;
    private Review review;
}
