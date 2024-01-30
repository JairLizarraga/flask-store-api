package com.ibm.flaskstoreapi.service;

import java.util.List;
import java.util.Optional;

import com.ibm.flaskstoreapi.model.Product;

import jakarta.validation.Valid;

public interface ProductService {

    List<Product> getProducts();
    Optional<Product> getProductById(int productId);
    Product addProduct(@Valid Product product);
    String updateProduct(@Valid Product product);
    String deleteProduct(@Valid Product product);
    
}
