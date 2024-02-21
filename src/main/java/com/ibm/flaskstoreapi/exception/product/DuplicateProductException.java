package com.ibm.flaskstoreapi.exception.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateProductException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public DuplicateProductException(String message) {
		super(message);
	}
	
	public DuplicateProductException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
