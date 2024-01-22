package com.ibm.flaskstoreapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Stock {

	@Id
	@GeneratedValue
	@NotNull
	private Integer stockId;

	@ManyToOne
	@JoinColumn(name="product_id")
	@NotNull
	private Product product;
	
	@ManyToOne
	@JoinColumn(name="store_id")
	@NotNull
	private Store store;
	
	@Column(name = "quantity")
	@Min(0)
	private int quantity;
	
	public Stock() {
		
	}
	
	public Stock(Store store, Product product, int quantity) {
		this.store = store;
		this.product = product;
		this.quantity = quantity;
	}
	
	public Integer getStockId() {
		return stockId;
	}
	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store= store;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Stock [stockId=" + stockId + ", product=" + product + ", store=" + store + ", quantity=" + quantity
				+ "]";
	}

	
}
