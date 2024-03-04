package com.ibm.flaskstoreapi.service;

import com.ibm.flaskstoreapi.model.Product;
import com.ibm.flaskstoreapi.repository.ProductRepository;
import com.ibm.flaskstoreapi.service.impl.ProductServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

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
    public void getProducts_success(){
        when(productRepository.findAll()).thenReturn(List.of(productMock));
        List<Product> products = productService.getProducts();
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
    }


    @Test
    public void getProducts_void(){
        when(productRepository.findAll()).thenReturn(List.of());
        List<Product> products = productService.getProducts();
        assertTrue(products.isEmpty());
        assertEquals(0, products.size());
    }

    @Test
    public void getProductById_success(){
        when(productRepository.findById(productMock.getProductId())).thenReturn(Optional.of(productMock));
        Product productById = productService.getProductById(productMock.getProductId());
        assertEquals(productById.getProductId(), productMock.getProductId());
    }

    @Test
    public void getProductById_ThrowsEntityNotFoundException(){
        when(productRepository.findById(productMock.getProductId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> productService.getProductById(productMock.getProductId()));
    }

    @Test
    public void getProductById_ThrowsIllegalArgumentException(){
        when(productRepository.findById(null)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> productService.getProductById(null));
    }


    @Test
    public void addProduct_success(){
        when(productRepository.existsById(productMock.getProductId())).thenReturn(false);
        when(productRepository.save(productMock)).thenReturn(productMock);
        Product product = productService.addProduct(productMock);
        assertEquals(product, productMock);
    }

    @Test
    public void addProduct_ThrowsDataIntegrityViolationException(){
        when(productRepository.existsById(productMock.getProductId())).thenReturn(true);
        assertThrows(DataIntegrityViolationException.class, () -> productService.addProduct(productMock));
    }

    @Test
    public void addProduct_ThrowsIllegalArgumentException(){
        when(productRepository.save(productMock)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> productService.addProduct(productMock));
    }

    @Test
    public void addProduct_ThrowsOptimisticLockingFailureException(){
        when(productRepository.save(productMock)).thenThrow(OptimisticLockingFailureException.class);
        assertThrows(OptimisticLockingFailureException.class, () -> productService.addProduct(productMock));
    }

    @Test
    public void addProduct_ThrowsRuntimeException(){
        when(productRepository.save(productMock)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> productService.addProduct(productMock));
    }

    @Test
    public void updateProduct_success(){
        when(productRepository.existsById(productMock.getProductId())).thenReturn(true);
        when(productRepository.save(productMock)).thenReturn(productMock);
        Product product = productService.updateProduct(productMock);
        assertEquals(product, productMock);
    }

    @Test
    public void updateProduct_ThrowsDataIntegrityViolationException(){
        when(productRepository.existsById(productMock.getProductId())).thenReturn(false);
        assertThrows(DataIntegrityViolationException.class, () -> productService.updateProduct(productMock));
    }

    @Test
    public void updateProduct_ThrowsIllegalArgumentException(){
        when(productRepository.existsById(productMock.getProductId())).thenReturn(true);
        when(productRepository.save(productMock)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(productMock));
    }

    @Test
    public void updateProduct_ThrowsOptimisticLockingFailureException(){
        when(productRepository.existsById(productMock.getProductId())).thenReturn(true);
        when(productRepository.save(productMock)).thenThrow(OptimisticLockingFailureException.class);
        assertThrows(OptimisticLockingFailureException.class, () -> productService.updateProduct(productMock));
    }

    @Test
    public void updateProduct_ThrowsRuntimeException(){
        when(productRepository.existsById(productMock.getProductId())).thenReturn(true);
        when(productRepository.save(productMock)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> productService.updateProduct(productMock));
    }


    @Test
    public void deleteProduct_success(){
        when(productRepository.existsById(productMock.getProductId())).thenReturn(true);
        String deleteResult = productService.deleteProduct(productMock.getProductId());
        assertEquals("Product with ID: " + productMock.getProductId() + " deleted", deleteResult);
    }


    @Test
    public void deleteProduct_ThrowsDataIntegrityViolation() {
        when(productRepository.existsById(productMock.getProductId())).thenReturn(false);
        assertThrows(DataIntegrityViolationException.class, () -> productService.deleteProduct(productMock.getProductId()));
    }

    @Test
    public void deleteProduct_ThrowsIllegalArgumentException() {
        when(productRepository.existsById(productMock.getProductId())).thenReturn(true);
        doThrow(IllegalArgumentException.class).when(productRepository).deleteById(productMock.getProductId());
        assertThrows(IllegalArgumentException.class, () -> productService.deleteProduct(productMock.getProductId()));
    }

    @Test
    public void deleteProduct_ThrowsOptimisticLockingFailureException() {
        when(productRepository.existsById(productMock.getProductId())).thenReturn(true);
        doThrow(OptimisticLockingFailureException.class).when(productRepository).deleteById(productMock.getProductId());
        assertThrows(OptimisticLockingFailureException.class, () -> productService.deleteProduct(productMock.getProductId()));
    }

    @Test
    public void deleteProduct_ThrowsRuntimeException() {
        when(productRepository.existsById(productMock.getProductId())).thenReturn(true);
        doThrow(RuntimeException.class).when(productRepository).deleteById(productMock.getProductId());
        assertThrows(RuntimeException.class, () -> productService.deleteProduct(productMock.getProductId()));
    }


}
