package com.alessandragodoy.customerms.exception;

/**
 * Exception thrown when accounts are not found.
 */
public class AccountsNotFoundException extends RuntimeException {
	public AccountsNotFoundException(String message) {
		super(message);
	}
}
