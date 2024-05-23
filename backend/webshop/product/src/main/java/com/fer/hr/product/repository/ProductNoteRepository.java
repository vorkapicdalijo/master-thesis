package com.fer.hr.product.repository;

import com.fer.hr.product.model.ProductNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductNoteRepository extends JpaRepository<ProductNote, Long> {

    @Modifying
    @Query("delete from ProductNote pn where pn.product.productId = :productId")
    void deleteProductNotesByProductProductId(@Param("productId") Long productId);
}
