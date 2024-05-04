package com.fer.hr.inventory.service;

import com.fer.hr.inventory.model.InventoryItem;
import com.fer.hr.inventory.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryService implements IInventoryService{

    private final InventoryRepository inventoryRepository;
    @Override
    public boolean isProductInStock(InventoryItem inventoryItemToCheck) {
        Optional<InventoryItem> currentInventoryItem = inventoryRepository.findById(inventoryItemToCheck.getProductId());
        if(currentInventoryItem.isPresent()) {
            return currentInventoryItem.get().getAmount() >= inventoryItemToCheck.getAmount();
        }
        return false;
    }

    @Override
    public void updateSingleProductAmount(InventoryItem inventoryItem) {
        Optional<InventoryItem> currentInventoryItem = inventoryRepository.findById(inventoryItem.getProductId());
        if(currentInventoryItem.isPresent()) {
            currentInventoryItem.get().setAmount(inventoryItem.getAmount());
            inventoryRepository.save(currentInventoryItem.get());
        }
        else {
            inventoryRepository.save(inventoryItem);
        }
    }

    @Override
    public void updateProductsAmountOnOrder(List<InventoryItem> inventoryOrderList) {
        for(InventoryItem inventoryItem : inventoryOrderList ) {
            updateSingleProductAmount(inventoryItem);
        }
    }
}
