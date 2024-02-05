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
import com.ibm.flaskstoreapi.service.StoreService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/store/")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Store>> getStores() {
        try{
        	return ResponseEntity.ok(storeService.getStores());
        } catch(Exception e) {
        	return ResponseEntity.internalServerError().build();
        }
        
    }

    @GetMapping("/{storeId}/")
    public ResponseEntity<Store> getStoreById(@PathVariable int storeId) {
    	try {
            return storeService.getStoreById(storeId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    	} catch(Exception e) {
    		return ResponseEntity.internalServerError().build();
    	}
    }

    @PostMapping("/")
    public ResponseEntity<String> addStore(@Valid @RequestBody Store store) {
        try {
            storeService.addStore(store);
            return ResponseEntity.status(HttpStatus.CREATED).body("Store added successfully");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Data integrity violation: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding store: " + e.getMessage());
        }
    }

    @PutMapping("/")
    public ResponseEntity<String> updateStore(@Valid @RequestBody Store store) {
    	return storeService.getStoreById(store.getStoreId())
    		.map(savedStore -> {
    			savedStore.setAddress(store.getAddress());
    			savedStore.setCity(store.getCity());
    			savedStore.setName(store.getName());
    			savedStore.setPhone(store.getPhone());
    			savedStore.setState(store.getState());
    			savedStore.setZipCode(store.getZipCode());
    			return ResponseEntity.ok("Updated successfully");
    		})
    		.orElse(ResponseEntity.notFound().build());
    	
    }


    @DeleteMapping("/{storeId}/")
    public ResponseEntity<String> deleteStore(@PathVariable int storeId) {
    	
    	Optional<Store> storeById = storeService.getStoreById(storeId);
    	if(storeById.isPresent()) {
    		return ResponseEntity.ok(storeService.deleteStore(storeService.getStoreById(storeId).get()));
    	} else {
    		return ResponseEntity.notFound().build();
    	}
    	
    }
}
