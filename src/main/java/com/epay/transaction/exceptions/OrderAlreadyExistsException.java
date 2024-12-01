package com.epay.transaction.exceptions;

public class OrderAlreadyExistsException extends RuntimeException{

	 public OrderAlreadyExistsException(String message) {
	        super(message);
	    }
}
