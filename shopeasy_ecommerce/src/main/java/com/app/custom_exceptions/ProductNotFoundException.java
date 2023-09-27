package com.app.custom_exceptions;

public class ProductNotFoundException extends Exception {
	public ProductNotFoundException(String msg) {
		super(msg);
	}
}
