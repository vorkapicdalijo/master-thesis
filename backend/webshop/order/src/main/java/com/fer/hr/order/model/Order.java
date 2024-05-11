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
public class Order {

    private Long id;
    private Date createdAt;
    private String payId;
    private String payerId;
    private float sum;
    private Person person;
    private List<CartItem> items;
}
