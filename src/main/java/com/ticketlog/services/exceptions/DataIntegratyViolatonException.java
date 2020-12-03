package com.ticketlog.services.exceptions;

public class DataIntegratyViolatonException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DataIntegratyViolatonException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataIntegratyViolatonException(String message) {
		super(message);
	}
	
	

}
