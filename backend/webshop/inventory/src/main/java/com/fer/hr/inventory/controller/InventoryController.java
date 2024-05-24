package com.fer.hr.inventory.controller;

import com.fer.hr.inventory.dto.InventoryItemRequest;
import com.fer.hr.inventory.service.InventoryService;
import com.fer.hr.inventory.service.impl.InventoryServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(InventoryController.class);


    @PostMapping("/check")
    public ResponseEntity<Boolean> isProductInStock(@RequestBody InventoryItemRequest inventoryItemRequest) {
        boolean isProductAvailable = inventoryService.isProductInStock(inventoryItemRequest);

        return new ResponseEntity<>(isProductAvailable, HttpStatus.OK);
    }

    @PostMapping("/product-update")
    public void updateInventoryProductAmount(@RequestBody InventoryItemRequest inventoryItemRequest) {
        log.info("Inventory update for product id = {} and new amount = {}", inventoryItemRequest.getProductId(), inventoryItemRequest.getAmount());
        inventoryService.updateSingleProductAmount(inventoryItemRequest);
    }

    @PostMapping("/products-order")
    public void updateProductsAmountOnOrder(@RequestBody List<InventoryItemRequest> inventoryItemRequestList) {
        inventoryService.updateProductsAmountOnOrder(inventoryItemRequestList);
    }

    @DeleteMapping("/remove/{productId}")
    public void removeProductFromInventory(@PathVariable("productId") Long productId) {
        inventoryService.removeProductFromInventory(productId);
    }
}
