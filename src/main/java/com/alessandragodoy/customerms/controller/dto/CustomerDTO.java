package com.alessandragodoy.customerms.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for Customer.
 *
 * @param customerId the ID of the customer
 * @param firstName  the first name of the customer
 * @param lastName   the last name of the customer
 * @param dni        the DNI of the customer
 * @param email      the email of the customer
 */
public record CustomerDTO(
		@Schema(description = "Unique identifier for the customer", example = "1")
		Integer customerId,
		@Schema(description = "First name of the customer", example = "Jane")
		@NotNull String firstName,
		@Schema(description = "Last name of the customer", example = "Doe")
		@NotNull String lastName,
		@Schema(description = "DNI of the customer", example = "12345678")
		@NotNull String dni,
		@Schema(description = "Email address of the customer", example = "jdoe@mail.com")
		@NotNull String email) {
}
