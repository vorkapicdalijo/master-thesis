package com.fer.hr.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"order\"")
public class Order {

    @Id
    @SequenceGenerator(
            name = "order_id_sequence",
            sequenceName = "order_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "order_id_sequence"
    )
    private Long id;
    private Date createdAt;
    private String payId;
    private String payerId;
    private float sum;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Person person;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<CartItem> items;
}
