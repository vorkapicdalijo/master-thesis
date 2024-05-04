package com.fer.hr.product.service;

import com.fer.hr.product.model.Product;
import com.fer.hr.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    @Override
    public Product getProductById(Integer productId) {
        return productRepository.findById(productId).get();

        // TODO: Get Product Reviews from the Review Microservice
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(Product product) {

        productRepository.saveAndFlush(product);

        // TODO: Update the Inventory ammount of the product in Inventory Microservice

        return product;
    }

    @Override
    public Product updateProduct(Integer productId, Product updatedProduct) {
        Product product = productRepository.findById(productId).get();

        product.setName(updatedProduct.getName());
        product.setCategoryId(updatedProduct.getCategoryId());
        product.setCategoryName(updatedProduct.getCategoryName());
        product.setCreatedAt(updatedProduct.getCreatedAt());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());

        productRepository.save(product);

        return product;
    }

    @Override
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);

        // TODO: Check if > 0 & Update the Inventory ammount of the product in Inventory Microservice

    }
}
