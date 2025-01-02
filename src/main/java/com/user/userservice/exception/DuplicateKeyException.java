package com.user.userservice.exception;

public class DuplicateKeyException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4002955079255537959L;

	public DuplicateKeyException(String message) {
        super(message);
    }
}
