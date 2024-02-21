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

import com.ibm.flaskstoreapi.exception.InternalServerException;
import com.ibm.flaskstoreapi.exception.product.DuplicateProductException;
import com.ibm.flaskstoreapi.exception.product.ProductNotFoundException;
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
		return Optional.ofNullable(productService.getProducts())
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new InternalServerException("Los productos no pueden ser encontrados debido a un error interno."));
	}
	
	@GetMapping("/{productId}/")
	public ResponseEntity<Product> getProductById(@PathVariable int productId) {
		return Optional.ofNullable(productService.getProductById(productId))
				.get()
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new ProductNotFoundException("Producto con id " + productId + " no pudo ser encontrado."));
	}
	
	@PostMapping("/")
	public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
		Optional<Product> productById = productService.getProductById(product.getProductId());
		if(productById.isPresent()) {
			throw new DuplicateProductException("Producto con id " + product.getProductId() + " duplicado.");			
		}
		
	    try {
	        Product savedProduct = productService.addProduct(product);
	        return ResponseEntity.ok(savedProduct);
	    } catch (RuntimeException e) {
	        throw new InternalServerException("Ocurrió un error al tratar de guardar el producto");
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
	            .orElseThrow(() -> new ProductNotFoundException("Product with ID " + product.getProductId() + " not found"));
	}

	@DeleteMapping("/")
	public ResponseEntity<String> deleteProduct(@Valid @RequestBody Product product) {
	    return productService.getProductById(product.getProductId())
	        .map(productToBeDeleted -> {
	            productService.deleteProduct(productToBeDeleted);
	            return ResponseEntity.ok("Product with ID: " + product.getProductId() + " deleted");
	        })
	        .orElseThrow(() -> new ProductNotFoundException("Product with ID " + product.getProductId() + " not found"));
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
