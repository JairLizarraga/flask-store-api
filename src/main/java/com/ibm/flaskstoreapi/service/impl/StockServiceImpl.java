package com.ibm.flaskstoreapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ibm.flaskstoreapi.model.Stock;
import com.ibm.flaskstoreapi.repository.StockRepository;
import com.ibm.flaskstoreapi.repository.StoreRepository;
import com.ibm.flaskstoreapi.service.StockService;

import jakarta.validation.Valid;

@Service
public class StockServiceImpl implements StockService{

	private final StockRepository stockRepository;
	private final StoreRepository storeRepository;

	public StockServiceImpl(StockRepository stockRepository, StoreRepository storeRepository) {
		this.stockRepository = stockRepository;
		this.storeRepository = storeRepository;
	}

	@Override
	public List<Stock> getStockAvailableInAllStores() {
		return stockRepository.findAll();
	}

	@Override
	public List<Stock> getStockAvailableInStore(int storeId) {
		return stockRepository.findByStore(storeRepository.findById(storeId).get());
	}

	@Override
	public Integer getProductAvailabilityInStore(int storeId, int productId) {
		try{
			Stock stock = getStock(storeId, productId);
			return stock.getQuantity();
		} catch(ResponseStatusException e) {
			return 0;
		}
	}

	@Override
	public Stock addStockToStore(@Valid Stock stock) {
    	Optional<Stock> stockAlreadyExists = stockRepository.findByStore_StoreIdAndProduct_ProductId(stock.getStore().getStoreId(), stock.getProduct().getProductId());
    	if(stockAlreadyExists.isPresent())
    		return updateProductInStore(stock);
		
       return stockRepository.save(stock);
	}

	@Override
	public Stock updateProductInStore(@Valid Stock stock) {
	    try {
	        Stock stockToBeUpdated = getStock(stock.getStore().getStoreId(), stock.getProduct().getProductId());
	        stockToBeUpdated.setQuantity(stock.getQuantity());
	        stockRepository.save(stockToBeUpdated);
	        return stockToBeUpdated;
	    } catch (ResponseStatusException e) {
	        return null;
	    }
	}

	@Override
	public String deleteProductFromStore(@Valid Stock stock) {
		Optional<Stock> stockToBeDeleted = stockRepository.findByStore_StoreIdAndProduct_ProductId(stock.getStore().getStoreId(), stock.getProduct().getProductId());
		
	    if (stockToBeDeleted.isPresent()) {
	        stockRepository.delete(stockToBeDeleted.get());
	        return "Stock deleted successfully";
	    } else {
	        return "Stock not found";
	    }
	}

	@Override
	public Stock getStock(int storeId, int productId) {
		Optional<Stock> stock = stockRepository.findByStore_StoreIdAndProduct_ProductId(storeId, productId);
		return stock.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not found"));
	}

}
