package com.ibm.flaskstoreapi.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Product {

	@Id
	@GeneratedValue
	private Integer productId;

	private String name;
	private String brand;
	private String model;
	private int price;
	private String sku;
	
	public Product() {
		
	}

	public Product(Integer productId, String name, String brand, String model, int price, String sku) {
		this.productId = productId;
		this.name = name;
		this.brand = brand;
		this.model = model;
		this.price = price;
		this.sku = sku;
	}



	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	
	
}
