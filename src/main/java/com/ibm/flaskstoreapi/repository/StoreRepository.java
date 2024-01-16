package com.ibm.flaskstoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.flaskstoreapi.model.Store;

public interface StoreRepository extends JpaRepository<Store, Integer>{

}
