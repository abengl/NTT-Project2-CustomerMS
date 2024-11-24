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

	@GetMapping("/account/{id}")
	public boolean customerExists(@PathVariable Integer id) {

		return customerService.customerExists(id);

	}

	@GetMapping
	public ResponseEntity<List<CustomerDTO>> getAllCustomers() {

		List<CustomerDTO> customers = customerService.getAllCustomers();
		return ResponseEntity.ok(customers);

	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer id) {

		CustomerDTO customer = customerService.getCustomerById(id);
		return ResponseEntity.ok(customer);

	}

	@PutMapping("/{id}")
	public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Integer id,
													  @RequestBody CustomerDTO customerDTO) {

		CustomerDTO updatedCustomer = customerService.updateCustomerById(id, customerDTO);
		return ResponseEntity.ok(updatedCustomer);

	}

	@PostMapping
	public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {

		CustomerDTO customer = customerService.createCustomer(customerDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(customer);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable Integer id) {

		CustomerDTO deletedCustomer = customerService.deleteCustomerById(id);
		return ResponseEntity.ok(deletedCustomer);

	}
}
