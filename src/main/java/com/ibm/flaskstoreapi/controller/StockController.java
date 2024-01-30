package com.ibm.flaskstoreapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.flaskstoreapi.model.Stock;
import com.ibm.flaskstoreapi.model.DAO.StockDao;
import com.ibm.flaskstoreapi.service.StockService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/stock")
public class StockController {

	private final StockService stockService;
	
	public StockController(StockService stockService) {
		this.stockService = stockService;
	}

	@GetMapping("/")
	public ResponseEntity<List<Stock>> getStockAvailableInAllStores(){
		return ResponseEntity.ok(stockService.getStockAvailableInAllStores());
	}
	 
	@GetMapping("/store_id/{storeId}/")
	public List<Stock> getStockAvailableInStore(@PathVariable int storeId){
		return stockService.getStockAvailableInStore(storeId);
	}
	
	@GetMapping("/store_id/{storeId}/product_id/{productId}/")
	public ResponseEntity<Integer> getProductAvailabilityInStore(@PathVariable int storeId, @PathVariable int productId) {
		return ResponseEntity.ok(stockService.getProductAvailabilityInStore(storeId, productId));
	}
	
    @PostMapping("/")
    public ResponseEntity<Stock> addStockToStore(@Valid @RequestBody StockDao stockDao) {
        return ResponseEntity.ok(stockService.addStockToStore(stockDao));
    }
   
	@PutMapping("/")
	public ResponseEntity<Stock> updateProductInStore(@Valid @RequestBody StockDao stock) {
		return ResponseEntity.ok(stockService.updateProductInStore(stock));
	}

	@DeleteMapping("/")
	public ResponseEntity<String> deleteProductFromStore(@Valid @RequestBody StockDao stockDao) {
		return ResponseEntity.ok(stockService.deleteProductFromStore(stockDao));
	}
	
}