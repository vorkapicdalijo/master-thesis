package com.fer.hr.product.service;

import com.fer.hr.product.dto.ProductRequest;
import com.fer.hr.product.dto.ProductResponse;
import com.fer.hr.product.model.*;

import java.util.List;

public interface ProductService {

    public ProductResponse getProductById(Long productId);
    public List<ProductResponse> getProducts();
    public ProductResponse addProduct(ProductRequest productRequest);
    public ProductResponse updateProduct(Long productId, ProductRequest productRequest);
    public Void deleteProduct(Long productId);


    public List<Brand> getBrands();
    public List<Type> getTypes();
    public List<Category> getCategories();
    public List<Note> getNotes();
    public List<NoteType> getNoteTypes();
}
