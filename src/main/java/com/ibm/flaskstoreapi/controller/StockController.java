package com.ibm.flaskstoreapi.controller;

import java.util.List;

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

import com.ibm.flaskstoreapi.model.Stock;
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
		try {
			return ResponseEntity.ok(stockService.getStockAvailableInAllStores());
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	 
	@GetMapping("/store_id/{storeId}/")
	public ResponseEntity<List<Stock>> getStockAvailableInStore(@PathVariable int storeId){
		try{
			return ResponseEntity.ok(stockService.getStockAvailableInStore(storeId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/store_id/{storeId}/product_id/{productId}/")
	public ResponseEntity<Integer> getProductAvailabilityInStore(@PathVariable int storeId, @PathVariable int productId) {
		return ResponseEntity.ok(stockService.getProductAvailabilityInStore(storeId, productId));
	}
	
    @PostMapping("/")
    public ResponseEntity<Stock> addStockToStore(@Valid @RequestBody Stock stock) {
        return ResponseEntity.ok(stockService.addStockToStore(stock));
    }
   
	@PutMapping("/")
	public ResponseEntity<Stock> updateProductInStore(@Valid @RequestBody Stock stock) {
		return ResponseEntity.ok(stockService.updateProductInStore(stock));
	}

	@DeleteMapping("/")
	public ResponseEntity<String> deleteProductFromStore(@Valid @RequestBody Stock stock) {
		return ResponseEntity.ok(stockService.deleteProductFromStore(stock));
	}
	
}