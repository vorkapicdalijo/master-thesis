package com.fer.hr.clients.inventory;

import com.fer.hr.clients.inventory.dto.InventoryItemRequest;
import com.fer.hr.clients.inventory.model.InventoryItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("inventory")
public interface InventoryClient {
    @GetMapping(path = "api/inventory/check")
    ResponseEntity<Boolean> isProductInStock(@RequestBody InventoryItemRequest inventoryItemRequest);

    @PostMapping(path = "api/inventory/product-update")
    void updateInventoryProductAmount(@RequestBody InventoryItemRequest inventoryItemRequest);

    @PostMapping(path = "api/inventory/products-order")
    void updateProductsAmountOnOrder(@RequestBody List<InventoryItemRequest> inventoryItemRequestList);

    @DeleteMapping(path = "/remove/{productId}")
    void removeProductFromInventory(@PathVariable("productId") Long productId);

}
