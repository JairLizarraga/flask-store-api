package com.ibm.flaskstoreapi.controller;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


import com.ibm.flaskstoreapi.model.Product;
import com.ibm.flaskstoreapi.model.Stock;
import com.ibm.flaskstoreapi.model.Store;
import com.ibm.flaskstoreapi.model.DAO.StockDao;
import com.ibm.flaskstoreapi.service.ProductService;
import com.ibm.flaskstoreapi.service.StockService;
import com.ibm.flaskstoreapi.service.StoreService;

@ExtendWith(MockitoExtension.class)
class StockControllerTest {
    
	@Mock
	private ProductService productService;

	@Mock
	private StoreService storeService;

	@Mock
	private StockService stockService;
	
	@InjectMocks
	private StockController stockController;

	private Stock stockMock;
	private Store storeMock;
	private Product productMock;
	private StockDao stockDaoMock;

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

	    storeMock = Store.builder()
	            .storeId(1001)
	            .address("address")
	            .city("city")
	            .name("name")
	            .phone("phone")
	            .state("state")
	            .stock(null)
	            .zipCode(null)
	            .build();
	    
    	stockMock = Stock.builder()
    			.product(Product.builder().productId(1).build())
    			.store(Store.builder().storeId(1).build())
    			.quantity(0)
    			.build();

		stockDaoMock = StockDao.builder()
				.productId(productMock.getProductId())
				.storeId(storeMock.getStoreId())
				.quantity(1)
				.build();
    }
    
    @Test
    @DisplayName("Get stock, success")
    public void testGetStockAvailableInAllStores_Success() {
    	List<Stock> mockStockList = java.util.Arrays.asList(stockMock);
    	
    	when(stockService.getStockAvailableInAllStores()).thenReturn(mockStockList);

    	ResponseEntity<List<Stock>> response = stockController.getStockAvailableInAllStores();
    	
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals(mockStockList.size(), response.getBody().size());
    	assertTrue(response.getBody().contains(stockMock));
    }
    
    @Test
    @DisplayName("Get stock, void list, success")
    public void testGetStockAvailableInAllStores_SuccessVoidList() {
    	List<Stock> mockStockList = java.util.Arrays.asList();
    	when(stockService.getStockAvailableInAllStores()).thenReturn(mockStockList);
    	
    	ResponseEntity<List<Stock>> stockAvailableInAllStores = stockController.getStockAvailableInAllStores();
    	assertEquals(HttpStatus.OK, stockAvailableInAllStores.getStatusCode());
    	assertEquals(0, stockAvailableInAllStores.getBody().size());
    }
    
    @Test
    @DisplayName("Get stock, fail")
    public void testGetStockAvailableInAllStores_Fail() {
    	when(stockService.getStockAvailableInAllStores()).thenThrow(RuntimeException.class);
    	ResponseEntity<List<Stock>> response = stockController.getStockAvailableInAllStores();
    	assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    
    @Test
    @DisplayName("Get stock in store, success")
    public void testGetStockAvailableInStore_Success() {

    	List<Stock> mockStockList = java.util.Arrays.asList(stockMock);
    	int storeId = stockMock.getStore().getStoreId();
    	
    	when(stockService.getStockAvailableInStore(storeId)).thenReturn(mockStockList);
    	ResponseEntity<List<Stock>> response = stockController.getStockAvailableInStore(storeId);

    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals(1, response.getBody().size());
    	
    	//when(stockRepository.findById(anyInt())).thenReturn()
    	//when(stockRepository.findByStore(anyInt())).
    }

    @Test
    @DisplayName("Get stock in store, fail")
    public void testGetStockAvailableInStore_Fail() {
    	int storeId = stockMock.getStore().getStoreId();
    	when(stockService.getStockAvailableInStore(anyInt())).thenThrow(RuntimeException.class);
    	ResponseEntity<List<Stock>> response = stockController.getStockAvailableInStore(storeId);
    	assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    
    
	public void testAddStockToStore_Success() throws Exception {		
		ResponseEntity<Stock> addStockToStore = stockController.addStockToStore(stockDaoMock);
		
		//assertEquals(, addStockToStore);
		
	    /*
	    when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
	    when(storeRepository.findById(anyInt())).thenReturn(Optional.of(store));
	    when(stockRepository.save(any(Stock.class))).thenReturn(new Stock(store, product, 10));
		*/
	    // Prepare request body
	    //StockDao stockDao = new StockDao();
	}


}
