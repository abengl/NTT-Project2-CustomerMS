package com.alessandragodoy.customerms;

import com.alessandragodoy.customerms.controller.CustomerController;
import com.alessandragodoy.customerms.controller.dto.CustomerDTO;
import com.alessandragodoy.customerms.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTests {

	@MockBean
	CustomerService customerService;

	@Autowired
	MockMvc mockMvc;

	List<CustomerDTO> customerDTOList = new ArrayList<>();

	@BeforeEach
	void setUp() {
		// Sample data
		customerDTOList.add(new CustomerDTO(1, "Jose", "Gomez", "12345678", "jose.gomez@mail.com"));
		customerDTOList.add(new CustomerDTO(2, "Maria", "Perez", "87654321", "maria.perez@mail.com"));
		customerDTOList.add(new CustomerDTO(3, "Juan", "Lopez", "45678912", "juan.lopez@mail.com"));
	}

	@Test
	@DisplayName("Test getAllCustomers - returns a CustomerDTO List with all clients")
	void getAllCustomers_ReturnsCustomerDTOList() throws Exception {
		when(customerService.getAllCustomers()).thenReturn(customerDTOList);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(customerDTOList.size()))
				.andExpect(jsonPath("$[0].dni").value("12345678"))
				.andExpect(jsonPath("$[1].email").value("maria.perez@mail.com"))
				.andDo(print());
	}

	@Test
	@DisplayName("Test getCustomerById - returns a CustomerDTO with client information")
	void getCustomerById_ReturnsCustomerDTO() throws Exception {
		int customerId = 1;

		when(customerService.getCustomerById(customerId)).thenReturn(customerDTOList.get(0));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/" + customerId)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.dni").value("12345678"))
				.andExpect(jsonPath("$.email").value("jose.gomez@mail.com"))
				.andDo(print());
	}

	@Test
	@DisplayName("Test updateCustomer - returns a CustomerDTO with client information updated")
	void updateCustomer_ReturnsCustomerDTO() throws Exception {
		int customerId = 2;
		CustomerDTO customerRequest = customerDTOList.get(1);

		when(customerService.updateCustomerById(customerId, customerRequest)).thenReturn(customerDTOList.get(1));

		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customers/" + customerId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(customerRequest))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.dni").value("87654321"))
				.andExpect(jsonPath("$.email").value("maria.perez@mail.com"))
				.andDo(print());
	}

	@Test
	@DisplayName("Test createCustomer - returns a CustomerDTO with client created")
	void createCustomer_ReturnsCustomerDTO() throws Exception {
		int customerId = 4;
		CustomerDTO customerRequest = new CustomerDTO(customerId, "Ale", "Luna", "77777777", "aluna@mail.com");
		customerDTOList.add(customerRequest);
		when(customerService.createCustomer(customerRequest)).thenReturn(customerRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(customerRequest))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.dni").value(customerRequest.dni()))
				.andExpect(jsonPath("$.email").value(customerRequest.email()))
				.andDo(print());
	}

	@Test
	@DisplayName("Test deleteCustomer - returns a CustomerDTO with client deleted")
	void deleteCustomer_ReturnsCustomerDTO() throws Exception {
		int customerId = 3;

		when(customerService.deleteCustomerById(customerId)).thenReturn(customerDTOList.get(2));

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/" + customerId)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.dni").value(customerDTOList.get(2).dni()))
				.andExpect(jsonPath("$.email").value(customerDTOList.get(2).email()))
				.andDo(print());
	}

	@Test
	@DisplayName("Test customerExists - returns a boolean related to customer registration")
	void customerExists_ReturnsBoolean() throws Exception {
		int customerId = 1;

		when(customerService.customerExists(customerId)).thenReturn(true);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/account/" + customerId)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").value(true))
				.andDo(print());
	}
}

