package com.ticketlog.services.exceptions;

public class ObjectNotFounfdException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ObjectNotFounfdException(String message, Throwable cause) {
		super(message, cause);
	}

	public ObjectNotFounfdException(String message) {
		super(message);
	}
	
}
