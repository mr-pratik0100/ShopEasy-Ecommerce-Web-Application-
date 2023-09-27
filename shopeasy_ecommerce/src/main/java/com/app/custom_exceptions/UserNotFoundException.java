package com.app.custom_exceptions;

public class UserNotFoundException extends Exception {
	public UserNotFoundException(String msg) {
		super(msg);
	}

}
