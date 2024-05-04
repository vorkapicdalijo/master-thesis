package com.fer.hr.product.service;

import com.fer.hr.product.dto.ProductRequest;
import com.fer.hr.product.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    public ProductResponse getProductById(Integer productId);
    public List<ProductResponse> getProducts();
    public ProductResponse addProduct(ProductRequest productRequest);
    public ProductResponse updateProduct(Integer productId, ProductRequest productRequest);
    public Void deleteProduct(Integer productId);
}
