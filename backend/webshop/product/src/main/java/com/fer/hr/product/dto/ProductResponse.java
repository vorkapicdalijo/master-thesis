package com.fer.hr.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Integer id;
    private String name;
    private String description;
    private Float price;
    private Integer categoryId;
    private String categoryName;
    private LocalDateTime createdAt;

    private Integer amount;
}
