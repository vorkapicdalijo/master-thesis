package com.fer.hr.inventory.model;

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
public class InventoryItem {

    @Id
    @SequenceGenerator(
            name = "inventory_id_sequence",
            sequenceName = "inventory_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "inventory_id_sequence"
    )
    private Integer productId;
    private Integer amount;
}
