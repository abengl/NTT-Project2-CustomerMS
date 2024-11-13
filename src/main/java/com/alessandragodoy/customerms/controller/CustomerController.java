package com.alessandragodoy.customerms.controller;

import com.alessandragodoy.customerms.controller.dto.CustomerDTO;
import com.alessandragodoy.customerms.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
	private final CustomerService customerService;

	@GetMapping
	public ResponseEntity<?> getAllCustomers(){
		List<CustomerDTO> customers = customerService.getAllCustomers();
		return ResponseEntity.ok(customers);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getCustomerById(@PathVariable int id){
		Optional<CustomerDTO> customer = customerService.getCustomerById(id);
		return customer.isPresent() ? ResponseEntity.ok(customer.get()) : ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable int id, @RequestBody CustomerDTO customerDTO){
		Optional<CustomerDTO> updatedCustomer = customerService.updateCustomerById(id, customerDTO);
		return updatedCustomer.isPresent() ? ResponseEntity.ok(updatedCustomer.get()) : ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> createCustomer(@RequestBody CustomerDTO customerDTO){
		CustomerDTO customer = customerService.createCustomer(customerDTO);

		return ResponseEntity.ok(customer);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable int id){
		Optional<CustomerDTO> deletedCustomer = customerService.deleteCustomerById(id);

		return deletedCustomer.isPresent() ? ResponseEntity.ok(deletedCustomer.get()) : ResponseEntity.notFound().build();
	}
}
