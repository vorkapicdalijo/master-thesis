package com.fer.hr.product.repository;

import com.fer.hr.product.model.ProductNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductNoteRepository extends JpaRepository<ProductNote, Long> {
}
