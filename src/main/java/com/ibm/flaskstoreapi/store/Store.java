package com.ibm.flaskstoreapi.store;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Store {

	@Id
	@GeneratedValue
	private Integer storeId;
	
	private String name;
	private String address;
	private String phone;
	private String city;
	private String state;
	private String zipCode;
	
	public Store() {
	}
	
	public Store(Integer storeId, String name, String address, String phone, String city, String state,
			String zipCode) {
		this.storeId = storeId;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	
	
}
