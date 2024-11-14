package com.alessandragodoy.customerms.controller.dto;

/**
 * Data Transfer Object for Customer.
 *
 * @param customerId the ID of the customer
 * @param firstName the first name of the customer
 * @param lastName the last name of the customer
 * @param dni the DNI of the customer
 * @param email the email of the customer
 */
public record CustomerDTO(Integer customerId, String firstName, String lastName, String dni, String email) {
}
