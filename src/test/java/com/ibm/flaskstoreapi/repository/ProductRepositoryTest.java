package com.ibm.flaskstoreapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ibm.flaskstoreapi.controller.ProductController;
import com.ibm.flaskstoreapi.model.Product;
import com.ibm.flaskstoreapi.model.Stock;


@DataJpaTest
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;
	
	@Mock 
	private ProductController productController;
	
	
	@Test
	public void saveProduct_success() {
		Product product = new Product(1, "Product 1", "Brand 1", "Model 1", 1001, "Sku 1", List.of(new Stock()));
		Product savedProduct = productRepository.save(product);
		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct.getProductId()).isGreaterThan(0);
	}
	
}
