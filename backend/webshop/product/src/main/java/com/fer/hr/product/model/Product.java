package com.fer.hr.product.model;

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
@Table(name = "product")
public class Product {

    @Id
    @SequenceGenerator(
            name = "product_id_sequence",
            sequenceName = "product_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "product_id_sequence"
    )
    private Long id;
    private String name;
    private String description;
    private Float price;
    private Float size;

    private Long categoryId;
    private Long typeId;
    private Long brandId;
}
