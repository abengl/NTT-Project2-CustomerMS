package com.alessandragodoy.customerms.controller;

import com.alessandragodoy.customerms.controller.dto.CustomerDTO;
import com.alessandragodoy.customerms.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.List;
import java.util.Optional;

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

	@GetMapping("/{id}")
	public ResponseEntity<?> getCustomerById(@PathVariable Integer id) {
		Optional<CustomerDTO> customer = customerService.getCustomerById(id);
		return customer.isPresent() ? ResponseEntity.ok(customer.get()) : ResponseEntity.notFound().build();
	}

	@GetMapping
	public ResponseEntity<?> getAllCustomers() {
		List<CustomerDTO> customers = customerService.getAllCustomers();
		return ResponseEntity.ok(customers);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable Integer id,
											@RequestBody CustomerDTO customerDTO) {
		Optional<CustomerDTO> updatedCustomer = customerService.updateCustomerById(id, customerDTO);
		return updatedCustomer.isPresent() ? ResponseEntity.ok(updatedCustomer.get()) : ResponseEntity.notFound()
				.build();
	}

	@PostMapping
	public ResponseEntity<?> createCustomer(@RequestBody CustomerDTO customerDTO) {
		CustomerDTO customer = customerService.createCustomer(customerDTO);

		return ResponseEntity.ok(customer);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable Integer id) {
		Optional<CustomerDTO> deletedCustomer = customerService.deleteCustomerById(id);

		return deletedCustomer.isPresent() ? ResponseEntity.ok(deletedCustomer.get()) : ResponseEntity.notFound()
				.build();
	}
}
