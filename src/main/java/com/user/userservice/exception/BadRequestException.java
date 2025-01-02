package com.user.userservice.exception;

public class BadRequestException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8886925956262170963L;

	public BadRequestException(String message) {
        super(message);
    }
}
