package com.fer.hr.clients.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private Long id;
    private String userId;
    private String userName;
    private int rating;
    private Long productId;
    private String comment;
    private Date createdAt;
}
