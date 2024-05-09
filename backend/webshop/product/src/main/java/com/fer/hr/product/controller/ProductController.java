package com.fer.hr.product.controller;

import com.fer.hr.product.dto.ProductRequest;
import com.fer.hr.product.dto.ProductResponse;
import com.fer.hr.product.service.impl.ProductServiceImpl;
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
@RequestMapping("api/products")
@AllArgsConstructor
public class ProductController {

    private final ProductServiceImpl productServiceImpl;
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        log.info("Getting products...");
        List<ProductResponse> productResponseList = productServiceImpl.getProducts();

        if(productResponseList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId) {
        log.info("Getting product by id={}", productId);
        ProductResponse productResponse = productServiceImpl.getProductById(productId);

        if(productResponse != null) {
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productToAdd) {
        ProductResponse newProduct = productServiceImpl.addProduct(productToAdd);

        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductRequest updatedProduct
    ) {
        log.info("Updating product of id={}, Product={}", productId, updatedProduct);
        if(updatedProduct != null) {
          ProductResponse productResponse = productServiceImpl.updateProduct(productId, updatedProduct);
          return new ResponseEntity<>(productResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long productId) {
        log.info("Deleting product by id={}", productId);
        productServiceImpl.deleteProduct(productId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
