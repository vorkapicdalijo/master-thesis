package com.fer.hr.inventory.service;

import com.fer.hr.inventory.model.InventoryItem;

import java.util.List;
import java.util.Map;

public interface IInventoryService {

    public boolean isProductInStock(InventoryItem inventoryItem);

    public void updateSingleProductAmount(InventoryItem inventoryItem);

    public void updateProductsAmountOnOrder(List<InventoryItem> inventoryOrderList);
}
