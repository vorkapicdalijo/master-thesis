package com.fer.hr.inventory.controller;

import com.fer.hr.inventory.dto.InventoryItemRequest;
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

    private final InventoryServiceImpl inventoryServiceImpl;
    private static final Logger log = LoggerFactory.getLogger(InventoryController.class);


    @GetMapping("/check")
    public ResponseEntity<Boolean> isProductInStock(@RequestBody InventoryItemRequest inventoryItemRequest) {
        boolean isProductAvailable = inventoryServiceImpl.isProductInStock(inventoryItemRequest);

        return new ResponseEntity<>(isProductAvailable, HttpStatus.OK);
    }

    @PostMapping("/product-update")
    public void updateInventoryProductAmount(@RequestBody InventoryItemRequest inventoryItemRequest) {
        log.info("Inventory update for product id = {} and new amount = {}", inventoryItemRequest.getProductId(), inventoryItemRequest.getAmount());
        inventoryServiceImpl.updateSingleProductAmount(inventoryItemRequest);
    }

    @PostMapping("/products-order")
    public void updateProductsAmountOnOrder(@RequestBody List<InventoryItemRequest> inventoryItemRequestList) {
        inventoryServiceImpl.updateProductsAmountOnOrder(inventoryItemRequestList);
    }
}
