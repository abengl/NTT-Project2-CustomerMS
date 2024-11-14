package com.alessandragodoy.customerms.service;

import com.alessandragodoy.customerms.controller.dto.CustomerDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing customers.
 * Provides methods for CRUD operations on customers.
 */
public interface CustomerService {
	/**
	 * Checks if a customer exists by their ID.
	 *
	 * @param customerId the ID of the customer
	 * @return true if the customer exists, false otherwise
	 */
	boolean customerExists(Integer customerId);

	/**
	 * Retrieves all customers.
	 *
	 * @return a list of CustomerDTO objects
	 */
	List<CustomerDTO> getAllCustomers();

	/**
	 * Retrieves a customer by their ID.
	 *
	 * @param customerId the ID of the customer
	 * @return an Optional containing the CustomerDTO if found, or empty if not found
	 */
	Optional<CustomerDTO> getCustomerById(Integer customerId);

	/**
	 * Updates a customer by their ID.
	 *
	 * @param customerId the ID of the customer
	 * @param customerDTO the customer data to update
	 * @return an Optional containing the updated CustomerDTO if successful, or empty if not found
	 */
	Optional<CustomerDTO> updateCustomerById(Integer customerId, CustomerDTO customerDTO);

	/**
	 * Creates a new customer.
	 *
	 * @param customerDTO the customer data to create
	 * @return the created CustomerDTO
	 */
	CustomerDTO createCustomer(CustomerDTO customerDTO);

	/**
	 * Deletes a customer by their ID.
	 *
	 * @param customerId the ID of the customer
	 * @return an Optional containing the deleted CustomerDTO if successful, or empty if not found
	 */
	Optional<CustomerDTO> deleteCustomerById(Integer customerId);
}
