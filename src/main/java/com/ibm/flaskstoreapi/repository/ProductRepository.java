package com.ibm.flaskstoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.flaskstoreapi.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
