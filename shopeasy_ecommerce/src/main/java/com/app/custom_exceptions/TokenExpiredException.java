package com.app.custom_exceptions;

public class TokenExpiredException extends Exception {
	public TokenExpiredException(String msg) {
		super(msg);
	}
}
