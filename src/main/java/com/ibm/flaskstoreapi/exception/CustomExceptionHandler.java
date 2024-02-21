package com.ibm.flaskstoreapi.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ibm.flaskstoreapi.exception.product.DuplicateProductException;
import com.ibm.flaskstoreapi.exception.product.ProductNotFoundException;

@ControllerAdvice
public class CustomExceptionHandler {


	@ExceptionHandler(value = {InternalServerException.class})
	public ResponseEntity<Object> internalServerException(InternalServerException exception){
		CustomExceptionTemplate apiException = new CustomExceptionTemplate(
				exception.getMessage(), 
				HttpStatus.INTERNAL_SERVER_ERROR, 
				ZonedDateTime.now(ZoneId.of("Z")));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiException);
	}
	
	@ExceptionHandler(value = {ProductNotFoundException.class})
	public ResponseEntity<Object> productNotFoundException(ProductNotFoundException exception){
		CustomExceptionTemplate apiException = new CustomExceptionTemplate(
				exception.getMessage(), 
				HttpStatus.NOT_FOUND, 
				ZonedDateTime.now(ZoneId.of("Z")));
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiException);
	}
	
	@ExceptionHandler(value = {DuplicateProductException.class})
	public ResponseEntity<Object> duplicateProductException(DuplicateProductException exception){
		CustomExceptionTemplate apiException = new CustomExceptionTemplate(
				exception.getMessage(), 
				HttpStatus.CONFLICT, 
				ZonedDateTime.now(ZoneId.of("Z")));
		return ResponseEntity.status(HttpStatus.CONFLICT).body(apiException);
	}
	
	
	
	
//	ProductNotFoundException: This exception is already implemented in your code and is thrown when a product with a specific ID is not found.
//	InvalidProductException: You can create this exception to handle cases where a product object is invalid or incomplete, such as when required fields are missing or have invalid values.
//	DuplicateProductException: This exception can be thrown when attempting to add a product that already exists in the system, based on some unique identifier like SKU or product ID.
//	InsufficientStockException: If you implement functionality to manage product stock, you can create this exception to handle cases where there is not enough stock available to fulfill a request, such as when placing an order.
//	UnauthorizedAccessException: This exception can be thrown when a user tries to perform an operation for which they do not have the necessary permissions, such as deleting a product without proper authorization.

}
