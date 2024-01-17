package com.ibm.flaskstoreapi.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.server.ResponseStatusException;

import com.ibm.flaskstoreapi.model.Product;
import com.ibm.flaskstoreapi.model.Stock;
import com.ibm.flaskstoreapi.model.Store;
import com.ibm.flaskstoreapi.model.DAO.StockDao;
import com.ibm.flaskstoreapi.repository.ProductRepository;
import com.ibm.flaskstoreapi.repository.StockRepository;
import com.ibm.flaskstoreapi.repository.StoreRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/stock")
public class StockController {

	private final StockRepository stockRepository;
	private final StoreRepository storeRepository;
	private final ProductRepository productRepository;

	public StockController (StockRepository stockRepository, StoreRepository storeRepository, ProductRepository productRepository) {
        this.stockRepository = stockRepository;
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
	}
	
	@GetMapping
	public ResponseEntity<List<Stock>> getStockAvailableInAllStores(){
		return ResponseEntity.ok(stockRepository.findAll());
	}
	 
	@GetMapping("/store_id/{storeId}")
	public List<Stock> getStockAvailableInStore(@PathVariable int storeId){
		return stockRepository.findByStore(storeRepository.findById(storeId).get());
	}
	
	@GetMapping("/store_id/{storeId}/product_id/{productId}")
	public ResponseEntity<Integer> getProductAvailabilityInStore(@PathVariable int storeId, @PathVariable int productId) {
		try{
			Stock stock = getStock(storeId, productId);
			return ResponseEntity.ok(stock.getQuantity());
		} catch(ResponseStatusException e) {
			return ResponseEntity.ok(0);
		}
	}
	
    @PostMapping
    public Stock addStockToStore(@RequestBody StockDao stockDao) {
    	Product product = productRepository.findById(stockDao.getProductId())
    	        .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + stockDao.getProductId()));
    	
    	Store store = storeRepository.findById(stockDao.getStoreId())
    	        .orElseThrow(() -> new EntityNotFoundException("Store not found with id: " + stockDao.getStoreId()));
    	
    	Stock stock = new Stock(store, product, stockDao.getQuantity());
        return stockRepository.save(stock);
    }
   
	@PutMapping
	public ResponseEntity<Stock> updateProductInStore(@RequestBody StockDao stock) {
	    try {
	        Stock stockToBeUpdated = getStock(stock.getStoreId(), stock.getProductId());
	        stockToBeUpdated.setQuantity(stock.getQuantity());
	        stockRepository.save(stockToBeUpdated);
	        return ResponseEntity.ok(stockToBeUpdated);
	    } catch (ResponseStatusException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	}

	@DeleteMapping
	public ResponseEntity<String> deleteProductFromStore(@RequestBody StockDao stockDao) {
		Optional<Stock> stockToBeDeleted = stockRepository.findByStore_StoreIdAndProduct_ProductId(stockDao.getStoreId(), stockDao.getProductId());
		
	    if (stockToBeDeleted.isPresent()) {
	        stockRepository.delete(stockToBeDeleted.get());
	        return ResponseEntity.ok("Stock deleted successfully");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock not found");
	    }
	}
    
	private Stock getStock(int storeId, int productId) {
		Optional<Stock> stock = stockRepository.findByStore_StoreIdAndProduct_ProductId(storeId, productId);
		return stock.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not found"));
	}
	
	

	
}
