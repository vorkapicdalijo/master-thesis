package com.fer.hr.review.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review {
    @Id
    @SequenceGenerator(
            name = "review_id_sequence",
            sequenceName = "review_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "review_id_sequence"
    )
    private Long id;
    private String userId;
    private String userName;
    private Float rating;
    private Long productId;
    private String comment;
}
