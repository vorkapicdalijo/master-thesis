package com.fer.hr.inventory.service;

import com.fer.hr.inventory.dto.InventoryItemRequest;
import com.fer.hr.inventory.model.InventoryItem;

import java.util.List;

public interface InventoryService {

    public boolean isProductInStock(InventoryItemRequest inventoryItemRequest);

    public void updateSingleProductAmount(InventoryItemRequest inventoryItemRequest);

    public void updateProductsAmountOnOrder(List<InventoryItemRequest> inventoryItemRequestList);
}
