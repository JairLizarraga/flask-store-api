package com.ibm.flaskstoreapi.service.impl;

import com.ibm.flaskstoreapi.model.Product;
import com.ibm.flaskstoreapi.repository.ProductRepository;
import com.ibm.flaskstoreapi.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
	public Product getProductById(Integer productId) {
		try {
			Optional<Product> productById = productRepository.findById(productId);
			if(productById.isPresent()){
				return productById.get();
			} else {
				throw new EntityNotFoundException("Product with id " + productById + " not found");
			}

		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid product data: " + e.getMessage());
		}
	}

	@Override
	public Product addProduct(@Valid Product product) {
		if(productRepository.existsById(product.getProductId())) {
			throw new DataIntegrityViolationException("Product already exists in the data base with id: " + product.getProductId());
		}
		try {
			return productRepository.save(product);
		} catch (IllegalArgumentException e){
			throw new IllegalArgumentException("Invalid product data: " + e.getMessage());
		} catch (OptimisticLockingFailureException e){
			throw new OptimisticLockingFailureException("Another update has occurred, please refresh and try again");
		} catch (RuntimeException e) {
			throw new RuntimeException("Failed to save product. Please try again later.", e);
		}
	}

	@Override
	public Product updateProduct(@Valid Product product) {
		if(productRepository.existsById(product.getProductId())) {
			try {
				return productRepository.save(product);
			} catch (IllegalArgumentException e){
				throw new IllegalArgumentException("Invalid product data: " + e.getMessage());
			} catch (OptimisticLockingFailureException e){
				throw new OptimisticLockingFailureException("Another update has occurred, please refresh and try again");
			} catch (RuntimeException e) {
				throw new RuntimeException("Failed to save product. Please try again later.", e);
			}
		} else {
			throw new DataIntegrityViolationException("Product with ID: " + product.getProductId() + " does not exist in the database. Add it first.");
		}
	}

	@Override
	public String deleteProduct(@Valid int productId) {
		if(!productRepository.existsById(productId)){
			throw new DataIntegrityViolationException("Nothing to delete");
		}
		try {
			productRepository.deleteById(productId);
			return "Product with ID: " + productId + " deleted";
	    }  catch (IllegalArgumentException e){
			throw new IllegalArgumentException("Invalid product data: " + e.getMessage());
		} catch (OptimisticLockingFailureException e){
			throw new OptimisticLockingFailureException("Another update has occurred, please refresh and try again");
		} catch (RuntimeException e) {
			throw new RuntimeException("Failed to save product. Please try again later.", e);
		}
		
	}

}
