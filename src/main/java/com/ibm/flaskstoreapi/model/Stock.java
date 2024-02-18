package com.ibm.flaskstoreapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

	public Stock(Store store, Product product, int quantity) {
	    this.store = store;
	    this.product = product;
	    this.quantity = quantity;
	}

	
}
