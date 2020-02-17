package com.gagan.banking.dao;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gagan.banking.model.CustAccount;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class AccountRepositoryTest {

	@Autowired
	AccountRepository accountRepo;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test_customer_balance_with_valid_acc_no() {
		String accNo = "ASQ-34571393372";
		Optional<CustAccount> optional = accountRepo.findByAccNo(accNo);
		Assert.assertTrue(optional.isPresent());
	}

	@Test
	public void test_customer_balance_with_invalid_acc_no() {
		String accNo = "ADASD";
		Optional<CustAccount> optional = accountRepo.findByAccNo(accNo);
		Assert.assertFalse(optional.isPresent());
	}

	@Test
	public void test_customer_balance_without_acc_no() {
		Optional<CustAccount> optional = accountRepo.findByAccNo(null);
		Assert.assertFalse(optional.isPresent());
	}
}
