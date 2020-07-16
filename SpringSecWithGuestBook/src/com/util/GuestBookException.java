package com.util;

public class GuestBookException extends RuntimeException {
	public GuestBookException(String message) {
		super(message);
	}

	public GuestBookException(String message, Throwable cause) {
		super(message, cause);
	}
}
