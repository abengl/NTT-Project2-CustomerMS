package com.alessandragodoy.customerms.service.impl;

import com.alessandragodoy.customerms.controller.dto.CustomerDTO;
import com.alessandragodoy.customerms.controller.dto.CustomerMapper;
import com.alessandragodoy.customerms.exception.AccountsNotFoundException;
import com.alessandragodoy.customerms.exception.CustomerValidationException;
import com.alessandragodoy.customerms.model.entity.Customer;
import com.alessandragodoy.customerms.repository.CustomerRepository;
import com.alessandragodoy.customerms.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Implementation of the CustomerService interface.
 * Provides methods for managing customers.
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final RestTemplate restTemplate;
	private final Predicate<String> isEmailValid = email -> email.matches("^[A-Za-z0-9_.-]+@[A-Za-z0-9.-]+$");
	private final Predicate<String> isDniValid = dni -> dni.matches("[0-9]{8}");
	@Value("${account.ms.url}")
	private String accountMsUrl;

	/* CustomerService methods */
	@Override
	public boolean customerExists(Integer customerId) {
		return customerRepository.existsById(customerId);
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		return customerRepository.findAll().stream().map(CustomerMapper::toDTO).toList();
	}

	@Override
	public CustomerDTO getCustomerById(Integer customerId) {
		return customerRepository.findById(customerId).map(CustomerMapper::toDTO)
				.orElseThrow(() -> new AccountsNotFoundException("Customer not found"));
	}

	@Override
	public CustomerDTO updateCustomerById(Integer customerId, CustomerDTO customerDTO) {
		validateCustomerData(customerDTO);
		return customerRepository.findById(customerId).map(existingCustomer -> {
			Customer updatedCustomer = CustomerMapper.toEntity(customerDTO);
			customerRepository.save(updatedCustomer);
			return CustomerMapper.toDTO(updatedCustomer);
		}).orElseThrow(() -> new AccountsNotFoundException("Customer not found"));
	}

	@Override
	public CustomerDTO createCustomer(CustomerDTO customerDTO) {
		validateCustomerData(customerDTO);
		Customer customer = CustomerMapper.dtoCreateToEntity(customerDTO);
		customerRepository.save(customer);
		return CustomerMapper.toDTO(customer);
	}

	@Override
	public CustomerDTO deleteCustomerById(Integer customerId) {
		if (customerHasAccounts(customerId)) {
			throw new CustomerValidationException("Customer has accounts and cannot be deleted.");
		}
		return customerRepository.findById(customerId).map(existingCustomer -> {
			customerRepository.delete(existingCustomer);
			return CustomerMapper.toDTO(existingCustomer);
		}).orElseThrow(() -> new AccountsNotFoundException("Customer not found"));
	}

	/* Helper methods */
	private boolean customerHasAccounts(Integer customerId) {
		String url = accountMsUrl + "/" + customerId;

		ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
		if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
			return response.getBody();
		}
		throw new AccountsNotFoundException("Error checking accounts in the microservice");
	}

	private void validateCustomerData(CustomerDTO customerDTO) {
		checkRequiredFields(customerDTO);
		checkDniFormat(customerDTO.dni());
		checkEmailFormat(customerDTO.email());
		checkDniUniqueness(customerDTO.dni(), customerDTO.customerId());
	}

	private void checkRequiredFields(CustomerDTO customerDTO) {
		if (customerDTO.firstName() == null || customerDTO.firstName()
				.isEmpty() || customerDTO.lastName() == null || customerDTO.lastName()
				.isEmpty() || customerDTO.dni() == null || customerDTO.dni()
				.isEmpty() || customerDTO.email() == null || customerDTO.email().isEmpty()) {
			throw new CustomerValidationException("First name, last name, DNI, and email are required.");
		}
	}

	private void checkDniFormat(String dni) {
		if (!isDniValid.test(dni)) {
			throw new CustomerValidationException("Invalid DNI format. It must contain exactly 8 digits.");
		}
	}

	private void checkEmailFormat(String email) {
		if (!isEmailValid.test(email)) {
			throw new CustomerValidationException(
					"Invalid email format. Format example 'user123@mail.com'");
		}

	}

	private void checkDniUniqueness(String dni, Integer customerId) {
		Optional<Customer> customerOptional = customerRepository.findByDni(dni);
		customerOptional.ifPresent(customer -> {
			if (!customer.getCustomerId().equals(customerId)) {
				throw new CustomerValidationException("DNI number is already registered.");
			}
		});
	}
}

