package com.ibm.flaskstoreapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.flaskstoreapi.model.Product;
import com.ibm.flaskstoreapi.repository.ProductRepository;

@RestController
@RequestMapping("/product/")
public class ProductController {

	private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
	
	@GetMapping
	public ResponseEntity<List<Product>> getProducts(){
		return ResponseEntity.ok(productRepository.findAll());
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable int productId) {
		Optional<Product> product = productRepository.findById(productId);
		return product.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		return ResponseEntity.ok(productRepository.save(product));
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteProduct(@RequestBody Product product) {
		try {
			productRepository.deleteById(product.getProductId());
			return ResponseEntity.ok("Product with ID: " + product.getProductId() + " deleted");
	    } catch (EmptyResultDataAccessException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID " + product.getProductId() + " not found");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting product: " + e.getMessage());
	    }
		
	}
	
	@PutMapping
	public ResponseEntity<String> updateProduct(@RequestBody Product product) {
		if(productRepository.existsById(product.getProductId())) {
			productRepository.save(product);
			return ResponseEntity.status(HttpStatus.CREATED).body("Product with ID: " + product.getProductId() + " updated");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	}
	
}
