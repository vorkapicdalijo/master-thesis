package com.fer.hr.product.mapper;

import com.fer.hr.product.dto.ProductRequest;
import com.fer.hr.product.dto.ProductResponse;
import com.fer.hr.product.model.Product;

public class ProductMapper {
    /*public static Product mapProductRequestToProduct(ProductRequest productRequest) {
        Product product = Product
                .builder()
                .id(productRequest.getId())
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .categoryId(productRequest.getCategoryId())
                .typeId(productRequest.getTypeId())
                .brandId(productRequest.getBrandId())
                .size(productRequest.getSize())
                .build();

        return product;
    }

    public static ProductResponse mapProductToProductResponse(Product product) {
        ProductResponse productResponse = ProductResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .categoryId(product.getCategoryId())
                .typeId(product.getTypeId())
                .brandId(product.getBrandId())
                .size(product.getSize())
                .build();

        return productResponse;
    }*/
}
