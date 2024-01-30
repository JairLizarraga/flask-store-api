package com.ibm.flaskstoreapi.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.ibm.flaskstoreapi.model.Stock;
import com.ibm.flaskstoreapi.model.DAO.StockDao;

import jakarta.validation.Valid;

public interface StockService {
	
	List<Stock> getStockAvailableInAllStores();
	List<Stock> getStockAvailableInStore(@PathVariable int storeId);
	Integer getProductAvailabilityInStore(@PathVariable int storeId, @PathVariable int productId);
	Stock addStockToStore(@Valid @RequestBody StockDao stockDao);
	Stock updateProductInStore(@Valid @RequestBody StockDao stock);
	String deleteProductFromStore(@Valid @RequestBody StockDao stockDao);
	Stock getStock(int storeId, int productId);
	
	
}
