package com.fer.hr.product.repository;

import com.fer.hr.product.model.SizePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SizePriceRepository extends JpaRepository<SizePrice, Long> {

    @Modifying
    @Query("delete from SizePrice sp where sp.product.productId = :productId")
    void deleteSizePricesByProductProductId(@Param("productId") Long productId);
}
