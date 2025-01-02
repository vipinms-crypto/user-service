package com.user.userservice.exception;

public class ResourceNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4824866751312616563L;

	public ResourceNotFoundException(String message) {
        super(message);
    }
}
