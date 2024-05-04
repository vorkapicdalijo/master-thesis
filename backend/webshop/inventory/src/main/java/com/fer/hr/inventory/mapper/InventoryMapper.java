package com.fer.hr.inventory.mapper;

import com.fer.hr.inventory.dto.InventoryItemRequest;
import com.fer.hr.inventory.model.InventoryItem;

public class InventoryMapper {
    public static InventoryItem mapInventoryItemRequestToInventoryItem(InventoryItemRequest inventoryItemRequest) {
        InventoryItem inventoryItem = InventoryItem
                .builder()
                .productId(inventoryItemRequest.getProductId())
                .amount(inventoryItemRequest.getAmount())
                .build();
        return inventoryItem;
    }
}
