package com.app.custom_exceptions;

public class ResourceNotFoundException extends Exception{
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
