package com.ibm.flaskstoreapi.controller;

import com.ibm.flaskstoreapi.exception.InternalServerException;
import com.ibm.flaskstoreapi.model.Product;
import com.ibm.flaskstoreapi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product/")
public class ProductController {

	private final ProductService productService;
		
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public ResponseEntity<List<Product>> getProducts(){
		return Optional.ofNullable(productService.getProducts())
			.map(ResponseEntity::ok)
			.orElseThrow(() -> new InternalServerException("Los productos no pueden ser encontrados debido a un error interno."));
	}
	
	@GetMapping("/{productId}/")
	public ResponseEntity<Product> getProductById(@PathVariable int productId) {

        return ResponseEntity.ok(productService.getProductById(productId));
	}

	@PostMapping("/")
	public ResponseEntity<String> addProduct(@Valid @RequestBody Product product) {
		productService.addProduct(product);
		return ResponseEntity.ok("Product with id " + product.getProductId() + " saved.");
	}
	
	@PutMapping("/")
	public ResponseEntity<String> updateProduct(@Valid @RequestBody Product product) {
		Product updatedProduct = productService.updateProduct(product);
		return ResponseEntity.ok("Updated successfully");
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable int productId) {
		String resultMessage = productService.deleteProduct(productId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(resultMessage);
	}


}
