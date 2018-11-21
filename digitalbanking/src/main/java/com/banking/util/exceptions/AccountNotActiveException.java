package com.banking.util.exceptions;

public class AccountNotActiveException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -353513000029514531L;

	public AccountNotActiveException() {
		super("Account is not active.");
	}
}
