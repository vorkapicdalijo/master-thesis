package com.fer.hr.product.repository;

import com.fer.hr.product.model.SizePrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizePriceRepository extends JpaRepository<SizePrice, Long> {
    //Long deleteByProduct_Id(Long productId);
}
