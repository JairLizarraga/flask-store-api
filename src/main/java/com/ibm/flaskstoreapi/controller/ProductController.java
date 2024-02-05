package com.ibm.flaskstoreapi.controller;

import java.util.List;
import java.util.Optional;

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
import com.ibm.flaskstoreapi.model.Stock;
import com.ibm.flaskstoreapi.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product/")
public class ProductController {

	private final ProductService productService;
		
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public ResponseEntity<List<Product>> getProducts(){
		try{
			return ResponseEntity.ok(productService.getProducts());
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	@GetMapping("/{productId}/")
	public ResponseEntity<Product> getProductById(@PathVariable int productId) {
		return productService
				.getProductById(productId)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/")
	public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
	    try {
	        Product savedProduct = productService.addProduct(product);
	        return ResponseEntity.ok(savedProduct);
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	@PutMapping("/")
	public ResponseEntity<String> updateProduct(@Valid @RequestBody Product product) {
	    return productService.getProductById(product.getProductId())
	            .map(savedProduct -> {
	                savedProduct.setBrand(product.getBrand());
	                savedProduct.setModel(product.getBrand());
	                savedProduct.setName(product.getName());
	                savedProduct.setPrice(product.getPrice());
	                savedProduct.setSku(product.getSku());
	                savedProduct.setStock(product.getStock());
	                productService.addProduct(savedProduct);
	                return ResponseEntity.ok("Updated successfully"); // Set the response body here
	            })
	            .orElse(ResponseEntity.notFound().build());
	}


	@DeleteMapping("/")
	public ResponseEntity<String> deleteProduct(@Valid @RequestBody Product product) {
		Optional<Product> productToBeDeleted = productService.getProductById(product.getProductId());
		if(productToBeDeleted.isPresent()) {
			productService.deleteProduct(productToBeDeleted.get());
			return ResponseEntity.ok("Product with ID: " + product.getProductId() + " deleted");
		} else {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID " + product.getProductId() + " not found");	
		}		
	}
	
	@GetMapping("/test")
	public Product getaProduct() {

	    Product prod = Product.builder()
	            .productId(1001)
	            .name(null)
	            .brand("brand")
	            .model("model")
	            .price(1001)
	            .sku("sku")
	            .stock(List.of(new Stock()))
	            .build();
	    
		return productService.addProduct(prod);
	}

}
