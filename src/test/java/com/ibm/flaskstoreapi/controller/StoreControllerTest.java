package com.ibm.flaskstoreapi.controller;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ibm.flaskstoreapi.model.Store;
import com.ibm.flaskstoreapi.service.StoreService;

@ExtendWith(MockitoExtension.class)
public class StoreControllerTest {

	@Mock
	private StoreService storeService;
	
	@InjectMocks
	private StoreController storeController;
	
	private Store storeMock;
	
	@BeforeEach
	public void setup() {
	    storeMock = Store.builder()
	            .storeId(1001)
	            .address("address")
	            .city("city")
	            .name("name")
	            .phone("phone")
	            .state("state")
	            .stock(null)
	            .zipCode(null)
	            .build();
	}

	
	@Test
	@DisplayName("Get stores, success")
	public void getStores_Success() {
		when(storeService.getStores()).thenReturn(List.of(storeMock));
		
		ResponseEntity<List<Store>> response = storeController.getStores();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().size());	
	}
	
	@Test
	@DisplayName("Get stores, fail")
	public void testGetStores_Fail() {
		when(storeService.getStores()).thenThrow(RuntimeException.class);
		ResponseEntity<List<Store>> response = storeController.getStores();
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		
	}
	
	@Test
	@DisplayName("Get store by Id, success")
	public void testGetStoreById_Success() {
		when(storeService.getStoreById(anyInt())).thenReturn(Optional.of(storeMock));
		
		ResponseEntity<Store> response = storeController.getStoreById(storeMock.getStoreId());
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(storeMock.getName(), response.getBody().getName());
	}
	
	@Test
	@DisplayName("Get store by Id, fail")
	public void testGetStoreById_Fail() {
		when(storeService.getStoreById(anyInt())).thenThrow(RuntimeException.class);
		ResponseEntity<Store> response = storeController.getStoreById(storeMock.getStoreId());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Add store, success")
	public void testAddStore_Success() {
		when(storeService.addStore(storeMock)).thenReturn(storeMock);
		
		ResponseEntity<String> response = storeController.addStore(storeMock);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertThat(response.getBody()).isNotNull();
	}
	
	@Test
	@DisplayName("Add store, fail")
	public void testAddStore_Fail() {
		when(storeService.addStore(storeMock)).thenThrow(RuntimeException.class);
		ResponseEntity<String> response= storeController.addStore(storeMock);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Update store, success")
	public void testUpdateStore_success() {
		when(storeService.getStoreById(storeMock.getStoreId())).thenReturn(Optional.of(storeMock));
		
		storeController.addStore(storeMock);
		storeMock.setName("Updated name");
		ResponseEntity<String> response = storeController.updateStore(storeMock);
		String name = storeController.getStoreById(storeMock.getStoreId()).getBody().getName();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Updated name", name);
	}
	
	@Test
	@DisplayName("Update store, fail")
	public void testUpdateStore_fail() {
		when(storeService.getStoreById(storeMock.getStoreId())).thenReturn(Optional.empty());
		ResponseEntity<String> result = storeController.updateStore(storeMock);
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
	}
	
	@Test
	@DisplayName("Delete store, success")
	public void testDeleteStore_Success() {
		when(storeService.getStoreById(storeMock.getStoreId())).thenReturn(Optional.empty());
		ResponseEntity<String> response = storeController.deleteStore(storeMock.getStoreId());
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
}
