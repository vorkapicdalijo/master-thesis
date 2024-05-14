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
    private ReviewId reviewId;
    private String userName;
    private int rating;
    private String comment;
    private Date createdAt;
}
