package com.fer.hr.inventory.service.impl;

import com.fer.hr.inventory.dto.InventoryItemRequest;
import com.fer.hr.inventory.mapper.InventoryMapper;
import com.fer.hr.inventory.model.InventoryItem;
import com.fer.hr.inventory.repository.InventoryRepository;
import com.fer.hr.inventory.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    @Override
    public boolean isProductInStock(InventoryItemRequest inventoryItemToCheck) {
        Optional<InventoryItem> currentInventoryItem = inventoryRepository.findById(inventoryItemToCheck.getProductId());
        if(currentInventoryItem.isPresent()) {
            return currentInventoryItem.get().getAmount() >= inventoryItemToCheck.getAmount();
        }
        return false;
    }

    @Override
    public void updateSingleProductAmount(InventoryItemRequest inventoryItemRequest) {
        Optional<InventoryItem> currentInventoryItem = inventoryRepository.findById(inventoryItemRequest.getProductId());
        if(currentInventoryItem.isPresent()) {
            currentInventoryItem.get().setAmount(inventoryItemRequest.getAmount());
            inventoryRepository.save(currentInventoryItem.get());
        }
        else {
            InventoryItem inventoryItem = InventoryMapper.mapInventoryItemRequestToInventoryItem(inventoryItemRequest);
            inventoryRepository.save(inventoryItem);
        }
    }

    @Override
    public void updateProductsAmountOnOrder(List<InventoryItemRequest> inventoryItemRequestList) {
        for(InventoryItemRequest inventoryItemRequest : inventoryItemRequestList ) {
            Integer newAmount = reduceProductAmount(inventoryItemRequest);
            inventoryItemRequest.setAmount(newAmount);
            updateSingleProductAmount(inventoryItemRequest);
        }
    }

    private Integer reduceProductAmount(InventoryItemRequest inventoryItemRequest) {
        Optional<InventoryItem> currentInventoryItem = inventoryRepository.findById(inventoryItemRequest.getProductId());

        if (currentInventoryItem.isPresent()) {
           Integer currentAmount = currentInventoryItem.get().getAmount();
           Integer newAmount = currentAmount - inventoryItemRequest.getAmount();

           return newAmount;
        }
        return null;
    }

    @Override
    public void removeProductFromInventory(Long productId) {
        Integer id = (int) (long) productId;
        inventoryRepository.deleteById(id);


    }
}
