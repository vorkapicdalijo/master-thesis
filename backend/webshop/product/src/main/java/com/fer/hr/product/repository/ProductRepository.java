package com.fer.hr.product.repository;

import com.fer.hr.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p " +
            "WHERE (:brandId IS NULL OR p.brand.brandId = :brandId) " +
            "AND (:typeId IS NULL OR p.type.typeId = :typeId) " +
            "AND (:categoryId IS NULL OR p.category.categoryId = :categoryId)")
    List<Product> findByBrandIdAndTypeIdAndCategoryId(
            @Param("brandId") Long brandId,
            @Param("categoryId") Long categoryId,
            @Param("typeId") Long typeId);
}
