package com.fer.hr.review.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review {
    @EmbeddedId
    private ReviewId reviewId;
    private String userName;
    private int rating;
    private String comment;
    private Date createdAt;
}
