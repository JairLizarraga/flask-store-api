package com.ibm.flaskstoreapi.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.ibm.flaskstoreapi.model.Product;
import com.ibm.flaskstoreapi.model.Stock;
import com.ibm.flaskstoreapi.model.Store;
import com.ibm.flaskstoreapi.model.DAO.StockDao;
import com.ibm.flaskstoreapi.repository.ProductRepository;
import com.ibm.flaskstoreapi.repository.StockRepository;
import com.ibm.flaskstoreapi.repository.StoreRepository;

import jakarta.validation.constraints.AssertTrue;

@ExtendWith(MockitoExtension.class)
class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
	@Mock
	private ProductRepository productRepository;

	@Mock
	private StoreRepository storeRepository;

	@Mock
	private StockRepository stockRepository;
	
	@InjectMocks
	private StockController stockController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
    }
    
    @Test
    public void testGetStockAvailableInAllStores_Success() {
    	Stock stock1 = new Stock();
    	Stock stock2 = new Stock();
    	
    	List<Stock> mockStockList = java.util.Arrays.asList(stock1, stock2);
    	
    	when(stockRepository.findAll()).thenReturn(mockStockList);

    	ResponseEntity<List<Stock>> stockAvailableInAllStores = stockController.getStockAvailableInAllStores();
    	List<Stock> body = stockAvailableInAllStores.getBody();
    	
    	assertEquals(HttpStatus.OK, stockAvailableInAllStores.getStatusCode());
    	assertEquals(mockStockList.size(), body.size());
    	assertTrue(body.contains(stock1));
    	assertTrue(body.contains(stock2));
    }
    
    @Test
    public void testGetStockAvailableInAllStores_SuccessVoidList() {
    	when(stockRepository.findAll()).thenReturn(Collections.emptyList());
    	
    	ResponseEntity<List<Stock>> stockAvailableInAllStores = stockController.getStockAvailableInAllStores();
    	assertEquals(HttpStatus.OK, stockAvailableInAllStores.getStatusCode());
    	assertEquals(0, stockAvailableInAllStores.getBody().size());
    }
    
    
    @Test
    public void testGetStockAvailableInStore_Success() {
    	
    	Integer mockStoreId = 1;
    	Store mockStore = new Store();
    	//when(stockRepository.findById(anyInt())).thenReturn()
    	//when(stockRepository.findByStore(anyInt())).
    }
    
	@Test
	public void testAddStockToStore_Success() throws Exception {
	    Product product = new Product();
	    Store store = new Store();
	    
	    when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
	    when(storeRepository.findById(anyInt())).thenReturn(Optional.of(store));
	    when(stockRepository.save(any(Stock.class))).thenReturn(new Stock(store, product, 10));

	    // Prepare request body
	    StockDao stockDao = new StockDao();

	    // Perform the request
	    mockMvc.perform(post("/stock/")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(stockDao)))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.quantity").value(10));
	}


}
