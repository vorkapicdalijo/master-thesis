package com.fer.hr.product.dto;


import com.fer.hr.product.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private Long productId;

    private String name;
    private String description;
    private String imageUrl;

    private Category category;

    private Type type;

    private Brand brand;

    private List<SizePrice> sizePrices;

    private List<ProductNote> productNotes;

    private Integer amount;
}
