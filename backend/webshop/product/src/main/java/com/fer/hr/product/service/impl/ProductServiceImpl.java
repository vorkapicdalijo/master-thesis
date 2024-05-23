package com.fer.hr.product.service.impl;

import com.fer.hr.clients.inventory.InventoryClient;
import com.fer.hr.clients.inventory.dto.InventoryItemRequest;
import com.fer.hr.clients.review.dto.AverageRatingAndCount;
import com.fer.hr.clients.review.ReviewClient;
import com.fer.hr.clients.review.dto.Review;
import com.fer.hr.product.dto.ProductRequest;
import com.fer.hr.product.dto.ProductResponse;
import com.fer.hr.product.mapper.ProductMapper;
import com.fer.hr.product.model.*;
import com.fer.hr.product.repository.*;
import com.fer.hr.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.hibernate.engine.jdbc.Size;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SizePriceRepository sizePriceRepository;
    private final ProductNoteRepository productNoteRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final TypeRepository typeRepository;
    private final NoteRepository noteRepository;
    private final NoteTypeRepository noteTypeRepository;
    private final InventoryClient inventoryClient;
    private final ReviewClient reviewClient;

    @Override
    public ProductResponse getProductById(Long productId) {
        Optional<Product> product =  productRepository.findById(productId);

        if(product.isEmpty()) {
            return null;
        }
        List<Review> productReviews = reviewClient.getReviewsByProductId(product.get().getProductId()).getBody();
        AverageRatingAndCount averageRatingAndCount = reviewClient.getAverageReviewsRatingAndCountByProductId(product.get().getProductId()).getBody();

        ProductResponse productResponse = ProductResponse.builder()
                .productId(product.get().getProductId())
                .name(product.get().getName())
                .description(product.get().getDescription())
                .imageUrl(product.get().getImageUrl())
                .brand(product.get().getBrand())
                .category(product.get().getCategory())
                .type(product.get().getType())
                .productNotes(product.get().getProductNotes())
                .sizePrices(product.get().getSizePrices())
                .reviews(productReviews)
                .averageRatingAndCount(averageRatingAndCount)
                .build();

        return productResponse;
    }

    @Override
    public List<ProductResponse> getProducts() {
        List<Product> productList = productRepository.findAll();

        List<ProductResponse> productResponseList = new ArrayList<>();
        productList.forEach(product -> {
            AverageRatingAndCount averageRatingAndCount = reviewClient.getAverageReviewsRatingAndCountByProductId(product.getProductId()).getBody();

            ProductResponse productResponse = ProductResponse.builder()
                    .productId(product.getProductId())
                    .description(product.getDescription())
                    .name(product.getName())
                    .type(product.getType())
                    .brand(product.getBrand())
                    .category(product.getCategory())
                    .imageUrl(product.getImageUrl())
                    .averageRatingAndCount(averageRatingAndCount)
                    //.productNotes(product.getProductNotes())
                    //.sizePrices(product.getSizePrices())
                    .build();

            productResponseList.add(productResponse);
        });
        Collections.reverse(productResponseList);
        return productResponseList;
    }

    @Override
    public ProductResponse addProduct(ProductRequest productRequest) {

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .imageUrl(productRequest.getImageUrl())
                .brand(productRequest.getBrand())
                .category(productRequest.getCategory())
                .type(productRequest.getType())
                .build();

        productRepository.saveAndFlush(product);

        List<SizePrice> sizePrices = new ArrayList<>();
        for (SizePrice sizePrice : productRequest.getSizePrices()) {
            sizePrice.setProduct(product);

            sizePrices.add(sizePrice);
        }
        sizePriceRepository.saveAll(sizePrices);

        List<ProductNote> productNotes = new ArrayList<>();
        for (ProductNote productNote : productRequest.getProductNotes()) {
            productNote.setProduct(product);

            productNotes.add(productNote);
        }
        productNoteRepository.saveAll(productNotes);

        ProductResponse productResponse = ProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .brand(product.getBrand())
                .category(product.getCategory())
                .type(product.getType())
                .productNotes(productNotes)
                .sizePrices(sizePrices)
                .amount(productRequest.getAmount())
                .build();

        // TODO: Update the Inventory ammount of the product in Inventory Microservice

        InventoryItemRequest inventoryItemRequest = InventoryItemRequest
                .builder()
                .productId(product.getProductId())
                .amount(productRequest.getAmount())
                .build();
        inventoryClient.updateInventoryProductAmount(inventoryItemRequest);

        return productResponse;
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long productId, ProductRequest productRequest) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            return null;
        }

        Product updatedProduct = Product.builder()
                .productId(product.get().getProductId())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .imageUrl(productRequest.getImageUrl())
                .brand(productRequest.getBrand())
                .category(productRequest.getCategory())
                .type(productRequest.getType())
                .build();

        productRepository.save(updatedProduct);

        sizePriceRepository.deleteSizePricesByProductProductId(updatedProduct.getProductId());
        productNoteRepository.deleteProductNotesByProductProductId(updatedProduct.getProductId());


        List<SizePrice> sizePrices = new ArrayList<>();
        for (SizePrice sizePrice : productRequest.getSizePrices()) {
            sizePrice.setProduct(updatedProduct);

            sizePrices.add(sizePrice);
        }
        sizePriceRepository.saveAll(sizePrices);

        List<ProductNote> productNotes = new ArrayList<>();
        for (ProductNote productNote : productRequest.getProductNotes()) {
            productNote.setProduct(updatedProduct);

            productNotes.add(productNote);
        }
        productNoteRepository.saveAll(productNotes);

        ProductResponse productResponse = ProductResponse.builder()
                .productId(updatedProduct.getProductId())
                .name(updatedProduct.getName())
                .description(updatedProduct.getDescription())
                .imageUrl(updatedProduct.getImageUrl())
                .brand(updatedProduct.getBrand())
                .category(updatedProduct.getCategory())
                .type(updatedProduct.getType())
                .productNotes(productNotes)
                .sizePrices(sizePrices)
                .amount(productRequest.getAmount())
                .build();



        return productResponse;
    }

    @Override
    public Void deleteProduct(Long productId) {
        productRepository.deleteById(productId);

        // TODO: Check if > 0 & Update the Inventory ammount of the product in Inventory Microservice
        return null;
    }

    @Override
    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }

    @Override
    public List<Type> getTypes() {
        return typeRepository.findAll();
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Note> getNotes() {
        return noteRepository.findAll();
    }

    @Override
    public List<NoteType> getNoteTypes() {
        return noteTypeRepository.findAll();
    }
}
