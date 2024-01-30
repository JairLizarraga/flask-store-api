package com.ibm.flaskstoreapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    @NotNull
    private Integer productId;

    @NotNull
    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Positive
    @Column(name = "price")
    private Integer price;

    @Column(name = "sku")
    private String sku;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Stock> stock;

    
    
    
	public Product() {
	}

	public Product(@NotNull Integer productId, @NotNull @Size(max = 255) String name, String brand, String model,
			@Positive Integer price, String sku, List<Stock> stock) {
		this.productId = productId;
		this.name = name;
		this.brand = brand;
		this.model = model;
		this.price = price;
		this.sku = sku;
		this.stock = stock;
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public List<Stock> getStock() {
		return stock;
	}

	public void setStock(List<Stock> stock) {
		this.stock = stock;
	}
    
    
}
