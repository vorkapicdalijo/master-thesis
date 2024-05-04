package com.fer.hr.inventory.controller;

import com.fer.hr.inventory.dto.InventoryItemRequest;
import com.fer.hr.inventory.model.InventoryItem;
import com.fer.hr.inventory.service.impl.InventoryServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/inventory")
@AllArgsConstructor
public class InventoryController {

    private final InventoryServiceImpl inventoryServiceImpl;

    @GetMapping("/check")
    public ResponseEntity<Boolean> isProductInStock(@RequestBody InventoryItemRequest inventoryItemRequest) {
        boolean isProductAvailable = inventoryServiceImpl.isProductInStock(inventoryItemRequest);

        return new ResponseEntity<>(isProductAvailable, HttpStatus.OK);
    }

    @PostMapping("/product-update")
    public void updateInventoryProductAmount(@RequestBody InventoryItemRequest inventoryItemRequest) {
        inventoryServiceImpl.updateSingleProductAmount(inventoryItemRequest);
    }

    @PostMapping("/products-order")
    public void updateProductsAmountOnOrder(@RequestBody List<InventoryItemRequest> inventoryItemRequestList) {
        inventoryServiceImpl.updateProductsAmountOnOrder(inventoryItemRequestList);
    }
}
