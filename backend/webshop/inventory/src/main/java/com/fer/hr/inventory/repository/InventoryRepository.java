package com.fer.hr.inventory.repository;

import com.fer.hr.inventory.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryRepository extends JpaRepository<InventoryItem, Integer> {

    @Modifying
    @Query("delete from InventoryItem ii where ii.productId = :productId")
    void deleteInventoryItemByProductId(@Param("productId") Integer productId);
}
