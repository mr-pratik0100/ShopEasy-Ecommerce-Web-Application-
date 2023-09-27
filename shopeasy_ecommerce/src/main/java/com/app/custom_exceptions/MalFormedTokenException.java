package com.app.custom_exceptions;

public class MalFormedTokenException extends Exception {
	public MalFormedTokenException(String msg) {
		super(msg);
	}
}
