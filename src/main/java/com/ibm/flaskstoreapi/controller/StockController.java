package com.ibm.flaskstoreapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


	@Autowired
	public StockRepository stockRepository;
	
	@Autowired
	public StoreRepository storeRepository;

	@Autowired
	public ProductRepository productRepository;
	
	@GetMapping("/")
	public List<Stock> getStock(){
		return stockRepository.findAll();
	}
	
    @PostMapping("/")
    public Stock addProductToStore(@RequestBody StockDao stockDao) {
    	System.out.println(stockDao.getStoreId());
    	System.out.println(stockDao.getProductId());
    	System.out.println(stockDao.getQuantity());
    	
    	Product product = productRepository.findById(stockDao.getProductId())
    	        .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + stockDao.getProductId()));

    	Store store = storeRepository.findById(stockDao.getStoreId())
    	        .orElseThrow(() -> new EntityNotFoundException("Store not found with id: " + stockDao.getStoreId()));

    	Stock stock = new Stock(store, product, stockDao.getQuantity());
        
        
        return stockRepository.save(stock);
    }
    
	
	@GetMapping("/storeId/{store_id}")
	public List<Stock> getStockInStore(@PathVariable int store_id){
		return stockRepository.findByStore(storeRepository.findById(1001).get());
	}
//	+ getProductAvailabilityInStore(int store_id, int product_id)
//	+ addProductToStore(int store_id, int product_id, int quantity)
//	+ removeProductToStore(int store_id, int product_id, int quantity)
//	+ deleteProductFromStore(int store_id, int product_id)
	
}
