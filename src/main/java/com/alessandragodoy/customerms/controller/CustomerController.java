package com.alessandragodoy.customerms.controller;

import com.alessandragodoy.customerms.controller.dto.CustomerDTO;
import com.alessandragodoy.customerms.service.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing customers.
 * Provides endpoints for CRUD operations on customers.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
@Tag(name = "Customers", description = "Controller for Customer")
public class CustomerController {
	private final CustomerService customerService;

	// CUSTOMER CRUD ENDPOINTS (READ, UPDATE, CREATE, DELETE)

	/**
	 * Retrieves a list of all customers.
	 *
	 * @return ResponseEntity containing the list of all customers
	 */
	@GetMapping
	public ResponseEntity<List<CustomerDTO>> getAllCustomers() {

		List<CustomerDTO> customers = customerService.getAllCustomers();
		return ResponseEntity.ok(customers);

	}

	/**
	 * Retrieves a customer by their ID.
	 *
	 * @param id the ID of the customer to be retrieved
	 * @return ResponseEntity containing the customer data
	 */
	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer id) {

		CustomerDTO customer = customerService.getCustomerById(id);
		return ResponseEntity.ok(customer);

	}

	/**
	 * Updates an existing customer by their ID.
	 *
	 * @param id          the ID of the customer to be updated
	 * @param customerDTO the customer data transfer object containing the updated details of the customer
	 * @return ResponseEntity containing the updated customer data
	 */
	@PutMapping("/{id}")
	public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Integer id,
													  @RequestBody CustomerDTO customerDTO) {

		CustomerDTO updatedCustomer = customerService.updateCustomerById(id, customerDTO);
		return ResponseEntity.ok(updatedCustomer);

	}

	/**
	 * Creates a new customer.
	 *
	 * @param customerDTO the customer data transfer object containing the details of the customer to be created
	 * @return ResponseEntity containing the created customer data
	 */
	@PostMapping
	public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {

		CustomerDTO customer = customerService.createCustomer(customerDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(customer);

	}

	/**
	 * Deletes a customer by their ID.
	 *
	 * @param id the ID of the customer to be deleted
	 * @return ResponseEntity containing the deleted customer data
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable Integer id) {

		CustomerDTO deletedCustomer = customerService.deleteCustomerById(id);
		return ResponseEntity.ok(deletedCustomer);

	}

	// ENDPOINT FOR ACCOUNT-MS

	/**
	 * Checks if a customer exists by their ID.
	 *
	 * @param id the ID of the customer to check
	 * @return true if the customer exists, false otherwise
	 */
	@GetMapping("/account/{id}")
	public boolean customerExists(@PathVariable Integer id) {

		return customerService.customerExists(id);

	}
}
