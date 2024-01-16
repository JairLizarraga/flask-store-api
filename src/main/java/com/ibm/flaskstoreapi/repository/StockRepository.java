package com.ibm.flaskstoreapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.flaskstoreapi.model.Stock;
import com.ibm.flaskstoreapi.model.Store;

public interface StockRepository extends JpaRepository<Stock, Integer>{
	
	public List<Stock> findByStore(Store store);
    Optional<Stock> findByStore_StoreIdAndProduct_ProductId(int storeId, int productId);
}
