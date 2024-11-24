package com.alessandragodoy.customerms;

import com.alessandragodoy.customerms.controller.dto.CustomerDTO;
import com.alessandragodoy.customerms.exception.AccountsNotFoundException;
import com.alessandragodoy.customerms.exception.CustomerValidationException;
import com.alessandragodoy.customerms.model.entity.Customer;
import com.alessandragodoy.customerms.repository.CustomerRepository;
import com.alessandragodoy.customerms.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTests {
	public List<Customer> customers = new ArrayList<>();

	@InjectMocks
	private CustomerServiceImpl customerService;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private RestTemplate restTemplate;

	@Value("${account.ms.url}")
	private String accountMsUrl;

	@BeforeEach
	public void setUp() {
		customers.add(Customer.builder()
				.firstName("Jose").lastName("Gomez")
				.dni("12345678").email("jose.gomez@mail.com").build());
		customers.add(Customer.builder()
				.firstName("Maria").lastName("Perez")
				.dni("87654321").email("maria.perze@mail.com").build());
		customers.add(Customer.builder()
				.firstName("Juan").lastName("Lopez")
				.dni("45678912").email("juan.lopez@mail.com").build()
		);
	}

	@Test
	@DisplayName("Test customerExists by id method - it returns a boolean")
	public void CustomerService_CustomerExists_ReturnsBoolean() {
		// Arrange
		int customerId = 1;
		when(customerRepository.existsById(customerId)).thenReturn(true);

		// Act
		boolean result1 = customerService.customerExists(customerId);

		// Assert
		assertTrue(result1);
	}

	@Test
	@DisplayName("Test getAllCustomers method - it returns CustomerDTO list")
	public void CustomerService_GetAllCustomers_ReturnsCustomerDTOList() {
		// Arrange
		when(customerRepository.findAll()).thenReturn(customers);

		// Act
		List<CustomerDTO> customersDTO = customerService.getAllCustomers();

		// Assert
		assertEquals(3, customersDTO.size());
		assertEquals("Jose", customersDTO.get(0).firstName());
	}

	@Test
	@DisplayName("Test getCustomerById method - it returns CustomerDTO")
	public void CustomerService_GetCustomerById_ReturnsCustomerDTO() {
		// Arrange
		int customerId = 1;
		when(customerRepository.findById(customerId)).thenReturn(Optional.ofNullable(customers.get(0)));

		// Act
		CustomerDTO customerDTO = customerService.getCustomerById(customerId);

		// Assert
		assertNotNull(customerDTO);
		assertEquals("12345678", customerDTO.dni());
	}

	@Test
	@DisplayName("Test getCustomerById method - it returns AccountsNotFoundException")
	public void CustomerService_GetCustomerById_ReturnsAccountsNotFoundException() {
		// Arrange
		int customerId = 100;
		when(customerRepository.findById(customerId)).thenReturn(Optional.ofNullable(null));

		// Act & Assert
		AccountsNotFoundException exception = assertThrows(AccountsNotFoundException.class,
				() -> customerService.getCustomerById(customerId));
		assertEquals("Customer not found", exception.getMessage());
	}

	@Test
	@DisplayName("Test updateCustomerById method - it returns CustomerDTO")
	public void CustomerService_UpdateCustomerById_ReturnsCustomerDTO() {
		// Arrange
		int customerId = 1;
		CustomerDTO customerDTORequest =
				new CustomerDTO(customerId, "Jose", "Gomez", "88888888", "jose.gomez@mail.com");
		Customer existingCustomer = customers.get(0);
		Customer customerUpdated = Customer.builder()
				.firstName("Jose").lastName("Gomez")
				.dni("88888888").email("jose.gomez@mail.com").build();
		when(customerRepository.findByDni(customerDTORequest.dni())).thenReturn(Optional.empty());
		when(customerRepository.findById(customerId)).thenReturn(Optional.ofNullable(existingCustomer));
		when(customerRepository.save(any(Customer.class))).thenReturn(customerUpdated);

		// Act
		CustomerDTO customerDTO = customerService.updateCustomerById(customerId, customerDTORequest);

		// Assert
		assertNotNull(customerDTO);
		assertEquals("88888888", customerDTO.dni());
		verify(customerRepository, times(1)).findByDni(customerDTORequest.dni());
		verify(customerRepository, times(1)).findById(customerId);
		verify(customerRepository, times(1)).save(any(Customer.class));
	}

	@Test
	@DisplayName("Test updateCustomerById method - it returns AccountsNotFoundException")
	public void CustomerService_UpdateCustomerById_ReturnsAccountsNotFoundException() {
		// Arrange
		int customerId = 100;
		CustomerDTO customerDTORequest =
				new CustomerDTO(customerId, "Ana", "Lopez", "00000000", "alopez@mail.com");
		when(customerRepository.findByDni(customerDTORequest.dni())).thenReturn(Optional.empty());
		when(customerRepository.findById(customerId)).thenReturn(Optional.ofNullable(null));

		// Act & Assert
		AccountsNotFoundException exception = assertThrows(AccountsNotFoundException.class,
				() -> customerService.updateCustomerById(customerId, customerDTORequest));
		assertEquals("Customer not found", exception.getMessage());
	}

	@Test
	@DisplayName("Test createCustomer method - it returns CustomerDTO")
	public void CustomerService_CreateCustomer_ReturnsCustomerDTO() {
		// Arrange
		CustomerDTO customerDTORequest =
				new CustomerDTO(4, "Ale", "Luna", "96385296", "ale.luna@mail.com");
		Customer newCustomer =
				Customer.builder().customerId(4).firstName("Ale").lastName("Luna").dni("96385296").email("ale" +
						".luna@mail.com").build();
		when(customerRepository.findByDni(customerDTORequest.dni())).thenReturn(Optional.empty());
		when(customerRepository.save(any(Customer.class))).thenReturn(newCustomer);

		// Act
		CustomerDTO customerDTO = customerService.createCustomer(customerDTORequest);

		// Assert
		assertNotNull(customerDTO);
		assertEquals("96385296", customerDTO.dni());
		verify(customerRepository, times(1)).findByDni(customerDTORequest.dni());
		verify(customerRepository, times(1)).save(any(Customer.class));
	}

	@Test
	@DisplayName("Test deleteCustomerById method - it returns CustomerDTO")
	public void CustomerService_DeleteCustomerById_ReturnsCustomerDTO() {
		// Arrange
		int customerId = 2;
		Customer existingCustomer = customers.get(customerId);
		String url = accountMsUrl + "/" + customerId;

		ResponseEntity<Boolean> responseEntity = new ResponseEntity<>(false, HttpStatus.OK);

		doReturn(responseEntity).when(restTemplate).getForEntity(url, Boolean.class);
		when(customerRepository.findById(customerId)).thenReturn(Optional.ofNullable(existingCustomer));
		doNothing().when(customerRepository).delete(existingCustomer);

		// Act
		CustomerDTO customerDTO = customerService.deleteCustomerById(customerId);


		// Assert
		assertNotNull(customerDTO);
		assertEquals("45678912", customerDTO.dni());
		verify(customerRepository, times(1)).findById(customerId);
		verify(customerRepository, times(1)).delete(existingCustomer);
	}

	@Test
	@DisplayName("Test deleteCustomerById method - it returns CustomerValidationException")
	public void CustomerService_DeleteCustomerById_ReturnsCustomerValidationException() {
		// Arrange
		int customerId = 2;
		String url = accountMsUrl + "/" + customerId;
		ResponseEntity<Boolean> responseEntity = new ResponseEntity<>(true, HttpStatus.OK);

		doReturn(responseEntity).when(restTemplate).getForEntity(url, Boolean.class);

		// Act & Assert
		CustomerValidationException exception = assertThrows(CustomerValidationException.class,
				() -> customerService.deleteCustomerById(customerId));
		assertEquals("Customer has accounts and cannot be deleted.", exception.getMessage());
	}
}
