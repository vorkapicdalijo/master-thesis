package com.fer.hr.inventory.controller;

import com.fer.hr.inventory.model.InventoryItem;
import com.fer.hr.inventory.service.InventoryService;
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

    private final InventoryService inventoryService;

    @GetMapping("/inventory-check")
    public ResponseEntity<Boolean> isProductInStock(@RequestBody InventoryItem inventoryItem) {
        boolean isProductAvailable = inventoryService.isProductInStock(inventoryItem);

        return new ResponseEntity<>(isProductAvailable, HttpStatus.OK);
    }

    @PostMapping("/product-amount/update")
    public void updateInventoryProductAmount(@RequestBody InventoryItem inventoryItem) {
        inventoryService.updateSingleProductAmount(inventoryItem);
    }

    @PostMapping("/products-order")
    public void updateProductsAmountOnOrder(@RequestBody List<InventoryItem> inventoryOrderList) {
        inventoryService.updateProductsAmountOnOrder(inventoryOrderList);
    }
}
