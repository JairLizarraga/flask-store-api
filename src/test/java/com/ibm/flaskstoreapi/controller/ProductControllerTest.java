package com.ibm.flaskstoreapi.controller;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ibm.flaskstoreapi.exception.InternalServerException;
import com.ibm.flaskstoreapi.exception.product.DuplicateProductException;
import com.ibm.flaskstoreapi.exception.product.ProductNotFoundException;
import com.ibm.flaskstoreapi.model.Product;
import com.ibm.flaskstoreapi.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

	@Mock
	private ProductService productService;
	
	@InjectMocks
	private ProductController productController;
	
	private Product productMock;
	
	@BeforeEach
	public void setup() {
	    productMock = Product.builder()
	            .productId(0)
	            .name("name")
	            .brand("brand")
	            .model("model")
	            .sku("sku")
	            .price(1001)
	            .build();
	}
	
	@Test
	@DisplayName("Get list of products, success")
	public void testGetProducts_Success() {
		List<Product> mockList = List.of(
			Product.builder().productId(1).name("name").price(1).sku("sku").build(),
			Product.builder().productId(2).name("name").price(1).sku("sku").build()
		);
		
		when(productService.getProducts()).thenReturn(mockList);		
		ResponseEntity<List<Product>> products = productController.getProducts();
		
		assertEquals(HttpStatus.OK, products.getStatusCode());
		assertEquals(mockList.size(), products.getBody().size());
	}

	@Test
	@DisplayName("Get void list of products, success")
	public void testGetProducts_VoidList() {
		List<Product> mockList = List.of();
		
		when(productService.getProducts()).thenReturn(mockList);		
		ResponseEntity<List<Product>> products = productController.getProducts();
		
		assertEquals(HttpStatus.OK, products.getStatusCode());
		assertEquals(0, products.getBody().size());
	}
	
	@Test
	@DisplayName("Get list of products, fails")
	public void testGetProducts_Failure() {
		when(productService.getProducts()).thenThrow(InternalServerException.class);
		assertThrows(InternalServerException.class, () -> {
			productController.getProducts();
		});
	}
	
	@Test
	@DisplayName("Get product by ID, success")
	public void testGetProductById_Success() {
		when(productService.getProductById(0)).thenReturn(Optional.of(productMock));
		ResponseEntity<Product> productById = productController.getProductById(0);
		
		assertEquals(HttpStatus.OK, productById.getStatusCode());
		assertEquals(productMock.getProductId(), productById.getBody().getProductId());
	}
	
	@Test
	@DisplayName("Get product by Id, fails")
	public void testGetProductById_Fail() {
		when(productService.getProductById(0)).thenReturn(Optional.empty());
		assertThrows(ProductNotFoundException.class, () -> {
			productController.getProductById(0);
		});
	}
	
	@Test
	@DisplayName("Add product, success")
	public void testAddProduct_Success() {
	    when(productService.addProduct(productMock)).thenReturn(productMock);
        ResponseEntity<Product> addProductResponse = productController.addProduct(productMock);        
        assertEquals(HttpStatus.OK, addProductResponse.getStatusCode());
        assertThat(addProductResponse).isNotNull();
	}
	
	@Test
	@DisplayName("Add product, internal server error")
	public void testAddProduct_Fail() {
	    when(productService.addProduct(productMock)).thenThrow(RuntimeException.class);
	    assertThrows(InternalServerException.class, () -> {
	    	productController.addProduct(productMock);
	    });
	}
	
    @Test
	@DisplayName("Add product, duplicated")
    public void testAddProduct_DuplicateProductId() {
        Product existingProduct = Product.builder().productId(123).build();
        when(productService.getProductById(existingProduct.getProductId())).thenReturn(Optional.of(existingProduct));
        DuplicateProductException exception = assertThrows(DuplicateProductException.class, () -> { 
        	productController.addProduct(existingProduct); 
        });
        assertEquals("Producto con id 123 duplicado.", exception.getMessage());
    }
	
	@Test
	@DisplayName("Update Product, success")
	public void testUpdateProduct_Success() {
		when(productService.getProductById(productMock.getProductId())).thenReturn(Optional.of(productMock));
		ResponseEntity<String> updateProduct = productController.updateProduct(productMock);
		assertEquals(HttpStatus.OK, updateProduct.getStatusCode());
		assertEquals("Updated successfully", updateProduct.getBody());
	}

	@Test
	@DisplayName("Update product, not found")
	public void testUpdateProduct_NotFound() {
		when(productService.getProductById(anyInt())).thenReturn(Optional.empty());
		assertThrows(ProductNotFoundException.class, () -> {
			productController.updateProduct(productMock);
		});
	}

	@Test
	@DisplayName("Delete product, success")
	public void testDeleteProduct_Success() {
		/*
		when(productService.getProductById(anyInt())).thenReturn(Optional.of(productMock));
		
		productController.addProduct(productMock);
		ResponseEntity<String> response = productController.deleteProduct(productMock);
		
		assertEquals("Product with ID: " + productMock.getProductId() + " deleted", response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		*/
		when(productService.getProductById(productMock.getProductId())).thenReturn(Optional.of(productMock));
		ResponseEntity<String> deleteProduct = productController.deleteProduct(productMock);
		assertEquals(HttpStatus.OK, deleteProduct.getStatusCode());
	}
	
	@Test
	@DisplayName("Delete product, fails")
	public void testDeleteProduct_Fail() {
		when(productService.getProductById(anyInt())).thenReturn(Optional.empty());
		
		assertThrows(ProductNotFoundException.class, () -> {
			productController.deleteProduct(productMock);
		});
	}
}
