package com.ibm.flaskstoreapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
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
	public String addStore(Store store) {
		try {
			storeRepository.save(store);
			return "Product added to the store";	 
		} catch (DataIntegrityViolationException e) {
			return "Data integrity violation: ";
		} catch (Exception e) {
			return "Error saving store: ";
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
	    Optional<Store> existingStore = storeRepository.findById(store.getStoreId());

	    if (existingStore.isPresent()) {
	        storeRepository.delete(store);
	        return "Store deleted successfully";
	    } else {
	        return "Store with ID " + store.getStoreId() + " not found";
	    }
	}
	
	
}
