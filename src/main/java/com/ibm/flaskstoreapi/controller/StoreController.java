package com.ibm.flaskstoreapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
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

import com.ibm.flaskstoreapi.model.Store;
import com.ibm.flaskstoreapi.repository.StoreRepository;


@RestController
@RequestMapping("/store/")
public class StoreController {

	private final StoreRepository storeRepository;
    
    public StoreController(StoreRepository productRepository) {
        this.storeRepository = productRepository;
    }
	
	@GetMapping
	public ResponseEntity<List<Store>> getStores(){
		return ResponseEntity.ok(storeRepository.findAll());
	}
	
	@GetMapping("/{storeId}")
	public ResponseEntity<Store> getStoreById(@PathVariable int storeId) {
		Optional<Store> store = storeRepository.findById(storeId);
		return store.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<String> addStore(@RequestBody Store store) {
		try {
			storeRepository.save(store);
			return ResponseEntity.status(HttpStatus.CREATED).body("Product added to the store");	
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Data integrity violation: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving store: " + e.getMessage());
		}
		
	}

	@PutMapping
	public ResponseEntity<String> updateStore(@RequestBody Store store) {
		if(storeRepository.existsById(store.getStoreId())) {
			storeRepository.save(store);
			return ResponseEntity.status(HttpStatus.CREATED).body("Store data updated");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Store with ID " + store.getStoreId() + " not found");
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteStore(@RequestBody Store store) {
	    Optional<Store> existingStore = storeRepository.findById(store.getStoreId());

	    if (existingStore.isPresent()) {
	        storeRepository.delete(store);
	        return ResponseEntity.ok("Store deleted successfully");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Store with ID " + store.getStoreId() + " not found");
	    }
	}

}
