package com.gagan.banking.dao;

import static org.junit.Assert.assertTrue;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gagan.banking.exception.ResourceNotFoundException;
import com.gagan.banking.model.Customer;
import com.gagan.banking.dao.CustomerRepository;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class CustomerRepositoryTest {

	@Autowired
	CustomerRepository customerRepo;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test_customer_by_cust_ogin() {
		String custLogin = "GAGAN001";
		Customer customer = customerRepo.findCustomerByCustLogin(custLogin);
		Assert.assertNotNull(customer);
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
