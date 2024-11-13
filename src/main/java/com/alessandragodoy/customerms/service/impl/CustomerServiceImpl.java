package com.alessandragodoy.customerms.service.impl;

import com.alessandragodoy.customerms.controller.dto.CustomerDTO;
import com.alessandragodoy.customerms.controller.dto.CustomerMapper;
import com.alessandragodoy.customerms.model.entity.Customer;
import com.alessandragodoy.customerms.repository.CustomerRepository;
import com.alessandragodoy.customerms.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;

	@Override
	public List<CustomerDTO> getAllCustomers() {
		return customerRepository.findAll().stream().map(CustomerMapper::entityToDTO).toList();
	}

	@Override
	public Optional<CustomerDTO> getCustomerById(int id) {
		return customerRepository.findById(id).map(CustomerMapper::entityToDTO);
	}

	@Override
	public Optional<CustomerDTO> updateCustomerById(int id, CustomerDTO customerDTO) {
		return customerRepository.findById(id).map(existingCustomer -> {
			Customer updatedCustomer = CustomerMapper.dtoUpdateToEntity(customerDTO);
			customerRepository.save(updatedCustomer);
			return CustomerMapper.entityToDTO(updatedCustomer);
		});
	}

	@Override
	public CustomerDTO createCustomer(CustomerDTO customerDTO) {
		Customer customer = CustomerMapper.dtoCreateToEntity(customerDTO);
		customerRepository.save(customer);
		return CustomerMapper.entityToDTO(customer);
	}

	@Override
	public Optional<CustomerDTO> deleteCustomerById(int id) {
		return customerRepository.findById(id).map(existingCustomer -> {
			customerRepository.delete(existingCustomer);
			return CustomerMapper.entityToDTO(existingCustomer);
		});
	}
}
