package com.alessandragodoy.customerms.controller.dto;

import com.alessandragodoy.customerms.exception.CustomerValidationException;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for Customer.
 *
 * @param customerId the ID of the customer
 * @param firstName the first name of the customer
 * @param lastName the last name of the customer
 * @param dni the DNI of the customer
 * @param email the email of the customer
 */
public record CustomerDTO(@NotNull Integer customerId, @NotNull String firstName, @NotNull String lastName,
						  @NotNull String dni, @NotNull String email) {
}
