package com.fer.hr.product.service;

import com.fer.hr.product.model.Product;

import java.util.List;

public interface IProductService {

    public Product getProductById(Integer productId);
    public List<Product> getProducts();
    public Product addProduct(Product product);
    public Product updateProduct(Integer productId, Product product);
    public void deleteProduct(Integer productId);
}
