package com.fer.hr.product.service.impl;

import com.fer.hr.clients.inventory.InventoryClient;
import com.fer.hr.clients.inventory.dto.InventoryItemRequest;
import com.fer.hr.clients.review.dto.AverageRatingAndCount;
import com.fer.hr.clients.review.ReviewClient;
import com.fer.hr.clients.review.dto.Review;
import com.fer.hr.product.dto.ProductRequest;
import com.fer.hr.product.dto.ProductResponse;
import com.fer.hr.product.mapper.ProductMapper;
import com.fer.hr.product.model.Product;
import com.fer.hr.product.repository.ProductRepository;
import com.fer.hr.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final InventoryClient inventoryClient;
    private final ReviewClient reviewClient;

    @Override
    public ProductResponse getProductById(Long productId) {
        Optional<Product> product =  productRepository.findById(productId);

        if(product.isEmpty()) {
            return null;
        }
        ProductResponse productResponse = ProductMapper.mapProductToProductResponse(product.get());
        List<Review> productReviews = reviewClient.getReviewsByProductId(product.get().getId()).getBody();
        productResponse.setReviews(productReviews);

        AverageRatingAndCount averageRatingAndCount = reviewClient.getAverageReviewsRatingAndCountByProductId(product.get().getId()).getBody();
        productResponse.setAverageRatingAndCount(averageRatingAndCount);

        return productResponse;
    }

    @Override
    public List<ProductResponse> getProducts() {
        List<Product> productList = productRepository.findAll();

        List<ProductResponse> productResponseList = new ArrayList<>();
        productList.forEach(product -> {
            ProductResponse productResponse = ProductMapper.mapProductToProductResponse(product);
            AverageRatingAndCount averageRatingAndCount = reviewClient.getAverageReviewsRatingAndCountByProductId(product.getId()).getBody();

            productResponse.setAverageRatingAndCount(averageRatingAndCount);
            productResponseList.add(productResponse);
        });

        return productResponseList;
    }

    @Override
    public ProductResponse addProduct(ProductRequest productRequest) {
        Product product = ProductMapper.mapProductRequestToProduct(productRequest);
        productRepository.saveAndFlush(product);

        ProductResponse productResponse = ProductMapper.mapProductToProductResponse(product);
        productResponse.setAmount(productRequest.getAmount());
        // TODO: Update the Inventory ammount of the product in Inventory Microservice

        InventoryItemRequest inventoryItemRequest = InventoryItemRequest
                .builder()
                .productId(product.getId())
                .amount(productRequest.getAmount()).build();
        inventoryClient.updateInventoryProductAmount(inventoryItemRequest);

        return productResponse;
    }

    @Override
    public ProductResponse updateProduct(Long productId, ProductRequest updatedProduct) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            product.get().setName(updatedProduct.getName());
            product.get().setCategoryId(updatedProduct.getCategoryId());
            product.get().setTypeId(updatedProduct.getTypeId());
            product.get().setBrandId(updatedProduct.getBrandId());
            product.get().setDescription(updatedProduct.getDescription());
            product.get().setPrice(updatedProduct.getPrice());
        }

        productRepository.save(product.get());

        ProductResponse productResponse = ProductMapper.mapProductToProductResponse(product.get());
        return productResponse;
    }

    @Override
    public Void deleteProduct(Long productId) {
        productRepository.deleteById(productId);

        // TODO: Check if > 0 & Update the Inventory ammount of the product in Inventory Microservice
        return null;
    }
}
