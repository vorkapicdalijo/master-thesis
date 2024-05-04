package com.fer.hr.clients.inventory;

import com.fer.hr.clients.inventory.model.InventoryItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("inventory")
public interface InventoryClient {
    @GetMapping(path = "api/inventory/inventory-check/{productId}")
    ResponseEntity<Boolean> isProductInStock(@PathVariable("productId") Integer productId);

    @PostMapping(path = "api/inventory/product-amount/update")
    void updateInventoryProductAmount(@RequestBody InventoryItem inventoryItem);

    @PostMapping(path = "api/inventory/products-order")
    void updateProductsAmountOnOrder(@RequestBody List<InventoryItem> inventoryOrderList);

}
