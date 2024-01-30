package com.ibm.flaskstoreapi.controller;

import java.util.List;

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
        return ResponseEntity.ok(storeService.getStores());
    }

    @GetMapping("/{storeId}/")
    public ResponseEntity<Store> getStoreById(@PathVariable int storeId) {
        return storeService.getStoreById(storeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
        String updateResult = storeService.updateStore(store);
        if (updateResult != null) {
            return ResponseEntity.ok(updateResult);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{storeId}/")
    public ResponseEntity<String> deleteStore(@PathVariable int storeId) {
        String deleteResult =  storeService.deleteStore(storeService.getStoreById(storeId).get());
        if (deleteResult != null) {
            return ResponseEntity.ok(deleteResult);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
