package com.ibm.flaskstoreapi.product;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/")
public class ProductController {

	private final ProductRepository productRepository;
    
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
	
	@PostMapping
	public Product addProduct(@RequestBody Product product) {
		return productRepository.save(product);
	}
	
	@DeleteMapping("/{productId}")
	public void deleteProduct(@PathVariable int productId) {
		productRepository.deleteById(productId);
	}
	
	@PutMapping("/{productId}")
	public void updateProduct(@PathVariable int productId, @RequestBody Product product) {
		if(productRepository.existsById(productId)) {
			product.setProductId(productId);
			productRepository.save(product);
		}
	}
	
	@GetMapping("/")
	public List<Product> getProducts(){
		return productRepository.findAll();
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable int productId) {
		Optional<Product> product = productRepository.findById(productId);
		return product.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
}
