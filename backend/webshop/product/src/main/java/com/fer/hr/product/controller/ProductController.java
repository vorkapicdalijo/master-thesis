package com.fer.hr.product.controller;

import com.fer.hr.product.model.Product;
import com.fer.hr.product.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.Port;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        log.info("Getting products...");
        List<Product> products = productService.getProducts();

        if(products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId) {
        log.info("Getting product by id={}", productId);
        Product product = productService.getProductById(productId);

        if(product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product productToAdd) {
        Product newProduct = productService.addProduct(productToAdd);

        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Integer productId,
            @RequestBody Product updatedProduct
    ) {
        log.info("Updating product of id={}, Product={}", productId, updatedProduct);
        if(updatedProduct != null) {
          Product product = productService.updateProduct(productId, updatedProduct);
          return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Integer productId) {
        log.info("Deleting product by id={}", productId);
        productService.deleteProduct(productId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
