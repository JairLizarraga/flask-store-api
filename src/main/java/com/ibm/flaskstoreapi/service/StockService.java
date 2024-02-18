package com.ibm.flaskstoreapi.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.ibm.flaskstoreapi.model.Stock;

import jakarta.validation.Valid;

public interface StockService {
	
	List<Stock> getStockAvailableInAllStores();
	List<Stock> getStockAvailableInStore(@PathVariable int storeId);
	Integer getProductAvailabilityInStore(@PathVariable int storeId, @PathVariable int productId);
	Stock addStockToStore(@Valid @RequestBody Stock stock);
	Stock updateProductInStore(@Valid @RequestBody Stock stock);
	String deleteProductFromStore(@Valid @RequestBody Stock stock);
	Stock getStock(int storeId, int productId);
	
	
}
