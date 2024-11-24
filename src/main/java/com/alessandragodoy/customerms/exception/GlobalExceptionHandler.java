package com.alessandragodoy.customerms.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application.
 * Handles specific and general exceptions and maps them to appropriate HTTP responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(AccountsNotFoundException.class)
	public ResponseEntity<String> handleCustomerNotFoundException(AccountsNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	@ExceptionHandler(CustomerValidationException.class)
	public ResponseEntity<String> handleValidationException(CustomerValidationException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<String> handleGeneralException(Exception e) {
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred. " + e.getMessage());
//	}
}
