package com.banking.util.exceptions;

public class UserAlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4277320601409186912L;

	public UserAlreadyExistsException() {
		super("User with the given login already exists");
	}
}
