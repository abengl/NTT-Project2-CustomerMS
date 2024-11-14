package com.alessandragodoy.customerms.controller.dto;

import com.alessandragodoy.customerms.model.entity.Customer;

/**
 * Mapper class for converting between Customer entity and CustomerDTO.
 */
public class CustomerMapper {
	public static CustomerDTO toDTO(Customer customer) {
		return new CustomerDTO(
				customer.getCustomerId(),
				customer.getFirstName(),
				customer.getLastName(),
				customer.getDni(),
				customer.getEmail()
		);
	}

	public static Customer toEntity(CustomerDTO customerDTO) {
		return Customer.builder()
				.customerId(customerDTO.customerId())
				.firstName(customerDTO.firstName())
				.lastName(customerDTO.lastName())
				.dni(customerDTO.dni())
				.email(customerDTO.email())
				.build();
	}

	public static Customer dtoCreateToEntity(CustomerDTO customerDTO) {
		return Customer.builder()
				.firstName(customerDTO.firstName())
				.lastName(customerDTO.lastName())
				.dni(customerDTO.dni())
				.email(customerDTO.email())
				.build();
	}
}
