package com.fer.hr.order.model;

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
@Table(name = "cart")
public class CartItem {
    @Id
    private Long productId;
    private String name;
    private Double price;
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName="id", nullable=false,unique=true)
    private Order order;
}
