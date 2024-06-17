package com.fer.hr.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fer.hr.product.dto.ProductRequest;
import com.fer.hr.product.dto.ProductResponse;
import com.fer.hr.product.service.ImageService;
import com.fer.hr.product.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ImageService imageService;
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long typeId,
            @RequestParam(required = false) Long categoryId
    ) {
        log.info("Getting products...");
        List<ProductResponse> productResponseList = productService.getProducts(brandId, categoryId, typeId);

        if(productResponseList.isEmpty()) {
            {
                "reviewId": {
                "productId": 52,
                        "userId": "93516232-2fae-4169-8425-2ef9fc3ce333"
            },
                "userName": "Dalijo",
                    "rating": 5,
                    "comment": "its awesome. i recommend it",
                    "createdAt": "2024-05-14T17:30:30.436+00:00"
            }

            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId) {
        log.info("Getting product by id={}", productId);
        ProductResponse productResponse = productService.getProductById(productId);

        if(productResponse != null) {
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductResponse> addProduct(@RequestParam("file") MultipartFile file, @RequestParam("product") String product) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            ProductRequest productToAdd = mapper.readValue(product, ProductRequest.class);

            String imageUrl = imageService.saveImageToStorage(file);
            productToAdd.setImageUrl(imageUrl);

            ProductResponse productResponse = productService.addProduct(productToAdd);

            return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId,
            @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam("product") String product
    ) {
        log.info("Updating product of id={}, Product={}", productId, product);
        try {
            ObjectMapper mapper = new ObjectMapper();
            ProductRequest productToUpdate = mapper.readValue(product, ProductRequest.class);
            if (productToUpdate.getImageUrl() == null && file != null) {
                String imageUrl = imageService.saveImageToStorage(file);
                productToUpdate.setImageUrl(imageUrl);
            }
            if (productToUpdate.getImageUrl() != null && file != null) {
                imageService.removeImageFromStorage(productToUpdate.getImageUrl());

                String imageUrl = imageService.saveImageToStorage(file);
                productToUpdate.setImageUrl(imageUrl);
            }

            ProductResponse productResponse = productService.updateProduct(productId, productToUpdate);
            return new ResponseEntity<>(productResponse, HttpStatus.OK);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long productId) {
        log.info("Deleting product by id={}", productId);
        productService.deleteProduct(productId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
