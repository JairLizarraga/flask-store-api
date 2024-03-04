package com.ibm.flaskstoreapi.service;

import com.ibm.flaskstoreapi.model.Product;
import jakarta.validation.Valid;

import java.util.List;

public interface ProductService {

    List<Product> getProducts();
    Product getProductById(Integer productId);
    Product addProduct(@Valid Product product);
    Product updateProduct(@Valid Product product);
    String deleteProduct(@Valid int productId);
    
}
