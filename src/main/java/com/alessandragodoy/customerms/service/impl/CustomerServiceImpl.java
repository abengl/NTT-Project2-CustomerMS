package com.alessandragodoy.customerms.service.impl;

import com.alessandragodoy.customerms.controller.dto.CustomerDTO;
import com.alessandragodoy.customerms.controller.dto.CustomerMapper;
import com.alessandragodoy.customerms.exception.AccountsNotFoundException;
import com.alessandragodoy.customerms.model.entity.Customer;
import com.alessandragodoy.customerms.repository.CustomerRepository;
import com.alessandragodoy.customerms.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final RestTemplate restTemplate;

	@Value("${account.ms.url}")
	private String accountMsUrl;

	private Predicate<String> isEmailValid = email -> email.matches("^[A-Za-z0-9_.-]+@[A-Za-z0-9.-]+$");
	private Predicate<String> isDniValid = dni -> dni.matches("[0-9]{8}");

	@Override
	public boolean customerExists(Integer customerId) {
		return customerRepository.existsById(customerId);
	}

	@Override
	public boolean customerHasAccounts(Integer customerId) {
		String url = accountMsUrl + "/" + customerId;
		try {
			ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				return response.getBody();
			}
		} catch (Exception e) {
			System.out.println("Error calling customer-ms: " + e.getMessage());
		}
		throw new AccountsNotFoundException("Account not found for ID: " + customerId);
	}

	@Override
	public Optional<CustomerDTO> deleteCustomerById(Integer customerId) {
		if (customerHasAccounts(customerId)) {
			throw new ValidationException("Customer has accounts and cannot be deleted.");
		}
		return customerRepository.findById(customerId).map(existingCustomer -> {
			customerRepository.delete(existingCustomer);
			return CustomerMapper.toDTO(existingCustomer);
		});
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		return customerRepository.findAll().stream().map(CustomerMapper::toDTO).toList();
	}

	@Override
	public Optional<CustomerDTO> getCustomerById(Integer customerId) {
		return customerRepository.findById(customerId).map(CustomerMapper::toDTO);
	}

	@Override
	public Optional<CustomerDTO> updateCustomerById(Integer customerId, CustomerDTO customerDTO) {
		validateCustomerData(customerDTO);
		return customerRepository.findById(customerId).map(existingCustomer -> {
			Customer updatedCustomer = CustomerMapper.toEntity(customerDTO);
			customerRepository.save(updatedCustomer);
			return CustomerMapper.toDTO(updatedCustomer);
		});
	}

	private void validateCustomerData(CustomerDTO customerDTO) {
		checkRequiredFields(customerDTO);
		checkDniFormat(customerDTO.dni());
		checkEmailFormat(customerDTO.email());
		checkDniUniqueness(customerDTO.dni(), customerDTO.customerId());
	}

	private void checkRequiredFields(CustomerDTO customerDTO) {
		if (customerDTO.firstName().isEmpty() || customerDTO.lastName().isEmpty() || customerDTO.dni().isEmpty() ||
				customerDTO.email().isEmpty()) {
			throw new ValidationException("First name, last name, DNI and email are required fields.");
		}
	}

	private void checkDniFormat(String dni) {
		if (!isDniValid.test(dni)) {
			throw new ValidationException("Invalid DNI format. It must contain exactly 8 digits.");
		}
	}

	private void checkEmailFormat(String email) {
		if (!isEmailValid.test(email)) {
			throw new ValidationException("Invalid email format. It must follow the following format " +
					"'user123@mail.com'");
		}

	}

	private void checkDniUniqueness(String dni, Integer customerId) {
		customerRepository.findByDni(dni).ifPresent(customer -> {
			if (!customer.getCustomerId().equals(customerId)) {
				throw new ValidationException("DNI must be unique.");
			}
		});
	}

	@Override
	public CustomerDTO createCustomer(CustomerDTO customerDTO) {
		validateCustomerData(customerDTO);
		Customer customer = CustomerMapper.dtoCreateToEntity(customerDTO);
		customerRepository.save(customer);
		return CustomerMapper.toDTO(customer);
	}
}
