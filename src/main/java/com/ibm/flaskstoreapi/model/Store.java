package com.ibm.flaskstoreapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {

	@Id
	@GeneratedValue
	@NotNull
	private Integer storeId;
	
	@Column(name = "name")
	@NotNull
	@Size(max = 255) 
	private String name;
	
	@Column(name = "address")
	@NotNull
	@Size(max = 255)
	private String address;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "city")
	@NotNull
	@Size(max = 255) 
	private String city;
	
	@Column(name = "state")
	@NotNull
	@Size(max = 255) 
	private String state;
	
	@Column(name = "zip_code")
	@Size(max = 5) 
	private String zipCode;
	
	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
	@JsonIgnore
	List<Stock> stock;

}
