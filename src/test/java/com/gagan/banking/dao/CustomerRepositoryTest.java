package com.gagan.banking.dao;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gagan.banking.exception.ResourceNotFoundException;
import com.gagan.banking.model.Customer;

@Transactional
@SpringBootTest
public class CustomerRepositoryTest {

	@Autowired
	CustomerRepository customerRepo;

	@Test
	public void test_customer_by_cust_ogin() {
		String custLogin = "GAGAN001";
		Customer customer = customerRepo.findCustomerByCustLogin(custLogin);
		assertNotNull(customer);
	}

	@Test
	public void test_customer_by_empty_data() {
		String custLogin = "";

		Exception exception = org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			customerRepo.findCustomerByCustLogin(custLogin);
		});
		String expectedMessage = "Resource doesn't exist";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.equals(expectedMessage));
	}

	@Test
	public void test_customer_by_invalid_data() {
		String custLogin = "ASW";

		Exception exception = org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			customerRepo.findCustomerByCustLogin(custLogin);
		});
		String expectedMessage = "Resource doesn't exist";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.equals(expectedMessage));
	}
}
