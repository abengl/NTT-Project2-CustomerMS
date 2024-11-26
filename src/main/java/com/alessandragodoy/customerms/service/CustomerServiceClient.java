package com.alessandragodoy.customerms.service;

import com.alessandragodoy.customerms.exception.ExternalServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * Client for interacting with the Account Microservice to check if a customer has accounts.
 */
@Component
public class CustomerServiceClient {
	private final RestTemplate restTemplate;
	private final String accountMsUrl;

	public CustomerServiceClient(RestTemplate restTemplate, @Value("${account.ms.url}") String accountMsUrl) {
		this.restTemplate = restTemplate;
		this.accountMsUrl = accountMsUrl;
	}

	public boolean customerHasAccounts(Integer customerId) {
		String url = accountMsUrl + "/" + customerId;
		try {
			ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
			return response.getStatusCode().is2xxSuccessful() && Boolean.TRUE.equals(response.getBody());
		} catch (ResourceAccessException e) {
			throw new ExternalServiceException("Unable to connect to the customer service." + e.getMessage());
		}
	}
}

