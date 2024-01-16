package com.ibm.flaskstoreapi.model.DAO;

public class StockDao {

	private Integer storeId;
	private Integer productId;
	private Integer quantity;
	
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(int store_id) {
		this.storeId = store_id;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(int product_id) {
		this.productId = product_id;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
