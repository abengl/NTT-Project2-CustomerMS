package com.alessandragodoy.customerms.service;

import com.alessandragodoy.customerms.controller.dto.CustomerDTO;
import com.alessandragodoy.customerms.exception.AccountsNotFoundException;
import com.alessandragodoy.customerms.exception.CustomerValidationException;
import com.alessandragodoy.customerms.exception.ExternalServiceException;

import java.util.List;

/**
 * Service interface for managing customers.
 * Provides methods for CRUD operations on customers.
 */
public interface CustomerService {
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
	 * @return the CustomerDTO if found
	 * @throws AccountsNotFoundException if the customer is not found
	 */
	CustomerDTO getCustomerById(Integer customerId);

	/**
	 * Updates a customer by their ID.
	 *
	 * @param customerId the ID of the customer
	 * @param customerDTO the customer data to update
	 * @return the updated CustomerDTO if successful
	 * @throws AccountsNotFoundException if the customer is not found
	 */
	CustomerDTO updateCustomerById(Integer customerId, CustomerDTO customerDTO);

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
	 * @return the deleted CustomerDTO if successful
	 * @throws AccountsNotFoundException if the customer is not found
	 * @throws CustomerValidationException if the customer has accounts and cannot be deleted
	 * @throws ExternalServiceException if there is an error connecting to the account service
	 */
	CustomerDTO deleteCustomerById(Integer customerId);

	/**
	 * Checks if a customer exists by their ID.
	 *
	 * @param customerId the ID of the customer
	 * @return true if the customer exists, false otherwise
	 */
	boolean customerExists(Integer customerId);
}
