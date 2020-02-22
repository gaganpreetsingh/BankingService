package com.gagan.banking.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gagan.banking.config.ConfigProperties;
import com.gagan.banking.dao.AccTransactionRepository;
import com.gagan.banking.dao.AccountRepository;
import com.gagan.banking.dao.CustomerRepository;
import com.gagan.banking.model.AccTransaction;
import com.gagan.banking.model.CustAccount;
import com.gagan.banking.model.Customer;
import com.gagan.banking.model.TransactionStatusEnum;
import com.gagan.banking.service.impl.AccountServiceImpl;
import com.gagan.banking.vo.AccTransactionVO;

@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

	@InjectMocks
	AccountServiceImpl accountService;

	@Mock
	CustomerRepository customerDAO;

	@Mock
	AccTransactionRepository accTxRepo;

	@Mock
	AccountRepository accDAO;
	
	@Mock
	ConfigProperties configProp;

	String accNo = "";
	String txStatus = "";
	String txTypeCode = "";
	Customer customer;
	CustAccount custAccount;
	List<AccTransaction> list = new ArrayList<>();

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		txStatus = TransactionStatusEnum.PROCESSED.name();
		txTypeCode = "CR";
		accNo = "ASQ-34571393372";
		//Creating customer
		customer = new Customer();
		customer.setActive(true);
		customer.setInterestRate(10);

		//Creating Account
		custAccount = new CustAccount();
		custAccount.setAccNo(accNo);
		custAccount.setAccBalance(12.22);
		custAccount.setActive(true);
		custAccount.setInterestAddedDate(Timestamp.valueOf(LocalDateTime.now().minus(Period.ofDays(1))));
		custAccount.setTbBnkCust(customer);
		
		AccTransaction accTx = new AccTransaction();
		accTx.setTxTypeCode("CR");
		accTx.setCustAcct(custAccount);
		accTx.setTxStatus(txStatus);
		list.add(accTx);
		custAccount.setAccTxList(list);

		List<CustAccount> custAccts = new ArrayList<>();
		custAccts.add(custAccount);
		customer.setCustAccts(custAccts);
		
	}

	@Test
	public void test_get_acc_activities_with_valid_data_test() {
		
		when(accDAO.findByAccNo(accNo)).thenReturn(Optional.of(custAccount));
		List<AccTransactionVO> accTxVOList = accountService.getTransactions(accNo, txTypeCode);
		assertNotNull(accTxVOList);

	}

	@Test
	public void test_calculate_interest_with_valid_cust_login() {

		when(accDAO.findByAccNo(accNo)).thenReturn(Optional.of(custAccount));
		when(configProp.getInterestRate()).thenReturn(5.0);
		AccTransactionVO accTxVO = accountService.calculateInterest(accNo);
		assertNotNull(accTxVO);
	}

	@Test
	public void test_calculate_interest_with_valid_cust_login_multiple_calls() {

		when(accDAO.findByAccNo(accNo)).thenReturn(Optional.of(custAccount));
		when(configProp.getInterestRate()).thenReturn(5.0);
		AccTransactionVO accTxVO = accountService.calculateInterest(accNo);
		assertNotNull(accTxVO);

		accTxVO = accountService.calculateInterest(accNo);
		assertTrue(accTxVO==null);
	}

	@Test
	public void test_get_balance_with_valid_data() {
		when(accDAO.findByAccNo(accNo)).thenReturn(Optional.of(custAccount));
		double result = accountService.getBalance(accNo);
		assertTrue(result>0);
	}
}
