package com.gagan.banking.dao;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gagan.banking.model.CustAccount;

@Transactional
@SpringBootTest
public class AccountRepositoryTest {

	@Autowired
	AccountRepository accountRepo;

	@Test
	public void test_customer_balance_with_valid_acc_no() {
		String accNo = "ASQ-34571393372";
		Optional<CustAccount> optional = accountRepo.findByAccNo(accNo);
		Assertions.assertTrue(optional.isPresent());
	}

	@Test
	public void test_customer_balance_with_invalid_acc_no() {
		String accNo = "ADASD";
		Optional<CustAccount> optional = accountRepo.findByAccNo(accNo);
		Assertions.assertFalse(optional.isPresent());
	}

	@Test
	public void test_customer_balance_without_acc_no() {
		Optional<CustAccount> optional = accountRepo.findByAccNo(null);
		Assertions.assertFalse(optional.isPresent());
	}
}
