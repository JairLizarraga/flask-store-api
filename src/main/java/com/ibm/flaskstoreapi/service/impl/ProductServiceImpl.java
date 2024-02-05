package com.ibm.flaskstoreapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ibm.flaskstoreapi.model.Product;
import com.ibm.flaskstoreapi.repository.ProductRepository;
import com.ibm.flaskstoreapi.service.ProductService;

import jakarta.validation.Valid;

@Service
public class ProductServiceImpl implements ProductService{

	private final ProductRepository productRepository;
	
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
	
	@Override
	public List<Product> getProducts() {
		return productRepository.findAll();
	}

	@Override
	public Optional<Product> getProductById(int productId) {
		return productRepository.findById(productId);
	}

	@Override
	public Product addProduct(@Valid Product product) {
		try {
			return productRepository.save(product);
		} catch (Exception e) {
			throw new RuntimeException("Failed to save product", e);
		}
	}

	@Override
	public String updateProduct(@Valid Product product) {
		if(productRepository.existsById(product.getProductId())) {
			productRepository.save(product);
			return "Product with ID: " + product.getProductId() + " updated";
		}
		return "Product not found";
	}

	@Override
	public String deleteProduct(@Valid Product product) {
		try {
			productRepository.deleteById(product.getProductId());
			return "Product with ID: " + product.getProductId() + " deleted";
	    } catch (EmptyResultDataAccessException e) {
	        return "Product with ID " + product.getProductId() + " not found";
	    } catch (Exception e) {
	        return "Error deleting product: " + e.getMessage();
	    }
		
	}

}
