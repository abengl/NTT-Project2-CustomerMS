package com.alessandragodoy.customerms.service.impl;

import com.alessandragodoy.customerms.controller.dto.CustomerDTO;
import com.alessandragodoy.customerms.controller.dto.CustomerMapper;
import com.alessandragodoy.customerms.model.entity.Customer;
import com.alessandragodoy.customerms.repository.CustomerRepository;
import com.alessandragodoy.customerms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final Predicate<String> isEmailValid = email -> email.matches("^[A-Za-z0-9_.-]+@[A-Za-z0-9.-]+$");
	private final Predicate<String> isDniValid = dni -> dni.matches("[0-9]{8}");
	private CustomerRepository customerRepository;
	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		return customerRepository.findAll().stream().map(CustomerMapper::toDTO).toList();
	}

	@Override
	public Optional<CustomerDTO> getCustomerById(int id) {
		return customerRepository.findById(id).map(CustomerMapper::toDTO);
	}

	@Override
	public Optional<CustomerDTO> updateCustomerById(int id, CustomerDTO customerDTO) {
		validateCustomerData(customerDTO);
		return customerRepository.findById(id).map(existingCustomer -> {
			Customer updatedCustomer = CustomerMapper.toEntity(customerDTO);
			customerRepository.save(updatedCustomer);
			return CustomerMapper.toDTO(updatedCustomer);
		});
	}

	private void validateCustomerData(CustomerDTO customerDTO) {
		checkRequiredFields(customerDTO);
		checkDniFormat(customerDTO.dni());
		checkEmailFormat(customerDTO.email());
		checkDniUniqueness(customerDTO.dni(), customerDTO.id());
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

	private void checkDniUniqueness(String dni, Integer id) {
		customerRepository.findByDni(dni).ifPresent(customer -> {
			if (!customer.getId().equals(id)) {
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

	@Override
	public Optional<CustomerDTO> deleteCustomerById(int id) {
		return customerRepository.findById(id).map(existingCustomer -> {
			customerRepository.delete(existingCustomer);
			return CustomerMapper.toDTO(existingCustomer);
		});
	}
}
