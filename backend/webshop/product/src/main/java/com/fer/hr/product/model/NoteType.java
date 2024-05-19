package com.fer.hr.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class NoteType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteTypeId;

    private String name;

    @OneToMany(mappedBy = "noteType")
    @JsonIgnore
    private List<ProductNote> productNotes;

}
