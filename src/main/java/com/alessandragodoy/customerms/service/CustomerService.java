package com.alessandragodoy.customerms.service;

import com.alessandragodoy.customerms.controller.dto.CustomerDTO;

import java.util.List;
import java.util.Optional;

 public interface CustomerService {
	 boolean customerExists(Integer customerId);
	 boolean customerHasAccounts(Integer customerId);
	 List<CustomerDTO> getAllCustomers();
	 Optional<CustomerDTO> getCustomerById(Integer customerId);
	 Optional<CustomerDTO> updateCustomerById(Integer customerId, CustomerDTO customerDTO);
	 CustomerDTO createCustomer(CustomerDTO customerDTO);
	 Optional<CustomerDTO> deleteCustomerById(Integer customerId);

}
