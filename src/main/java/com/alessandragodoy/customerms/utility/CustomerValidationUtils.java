package com.alessandragodoy.customerms.utility;

import com.alessandragodoy.customerms.exception.CustomerValidationException;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

/**
 * Utility class for validating customer-related fields.
 */
@RequiredArgsConstructor
public class CustomerValidationUtils {
	private static final Predicate<String> IS_EMAIL_VALID =
			email -> email.matches("^[A-Za-z0-9_.-]+@[A-Za-z0-9.-]+$");
	private static final Predicate<String> IS_DNI_VALID =
			dni -> dni.matches("[0-9]{8}");

	public static void checkRequiredFields(String firstName, String lastName, String dni, String email) {
		if (isNullOrEmpty(firstName) || isNullOrEmpty(lastName) || isNullOrEmpty(dni) || isNullOrEmpty(email)) {
			throw new CustomerValidationException("First name, last name, DNI, and email are required.");
		}
	}

	public static void checkDniFormat(String dni) {
		if (!IS_DNI_VALID.test(dni)) {
			throw new CustomerValidationException("Invalid DNI format. It must contain exactly 8 digits.");
		}
	}

	public static void checkEmailFormat(String email) {
		if (!IS_EMAIL_VALID.test(email)) {
			throw new CustomerValidationException("Invalid email format. Format example 'user123@mail.com'");
		}
	}

	private static boolean isNullOrEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}
}
