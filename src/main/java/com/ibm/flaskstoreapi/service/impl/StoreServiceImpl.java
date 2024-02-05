package com.ibm.flaskstoreapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ibm.flaskstoreapi.model.Store;
import com.ibm.flaskstoreapi.repository.StoreRepository;
import com.ibm.flaskstoreapi.service.StoreService;

import jakarta.validation.Valid;

@Service
public class StoreServiceImpl implements StoreService{

	private final StoreRepository storeRepository;
	
	public StoreServiceImpl(StoreRepository storeRepository) {
		this.storeRepository = storeRepository;
	}

	@Override
	public List<Store> getStores() {
		return storeRepository.findAll();
	}

	@Override
	public Optional<Store> getStoreById(int storeId) {
		return storeRepository.findById(storeId);
	}

	@Override
	public Store addStore(Store store) {
		try {
			return storeRepository.save(store); 
		} catch (Exception e) {
			throw new RuntimeException("Failed to save product", e);
		}
		
	}

	@Override
	public String updateStore(@Valid Store store) {
		if(storeRepository.existsById(store.getStoreId())) {
			storeRepository.save(store);
			return "Store data updated";
		}
		return "Store with ID " + store.getStoreId() + " not found";
	}

	@Override
	public String deleteStore(@Valid Store store) {
		try {
			storeRepository.findById(store.getStoreId());
			return "Store with ID " + store.getStoreId() + " deleted";
		} catch (EmptyResultDataAccessException e) {
			return "Product with ID " + store.getStoreId() + " not found";
		}	catch (Exception e) {
			return "Error deleting product: " + e.getMessage();
		}
	}
	
	
}
