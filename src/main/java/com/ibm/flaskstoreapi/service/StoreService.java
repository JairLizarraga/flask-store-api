package com.ibm.flaskstoreapi.service;

import java.util.List;
import java.util.Optional;

import com.ibm.flaskstoreapi.model.Store;

import jakarta.validation.Valid;

public interface StoreService {

    List<Store> getStores();
    Optional<Store> getStoreById(int storeId);
    Store addStore(@Valid Store store);
    String updateStore(@Valid Store store);
    String deleteStore(@Valid Store store);
}
