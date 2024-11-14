package com.alessandragodoy.customerms.exception;

public class AccountsNotFoundException extends RuntimeException {
	public AccountsNotFoundException(String message) {
		super(message);
	}
}
