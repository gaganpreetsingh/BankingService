package com.gagan.banking.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gagan.banking.config.ConfigProperties;
import com.gagan.banking.dao.AccTransactionRepository;
import com.gagan.banking.dao.CustomerRepository;
import com.gagan.banking.model.AccTransaction;
import com.gagan.banking.model.CustAccount;
import com.gagan.banking.model.Customer;
import com.gagan.banking.model.TransactionStatusEnum;
import com.gagan.banking.service.impl.CustomerServiceImpl;
import com.gagan.banking.vo.AccTransactionVO;

@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

	@InjectMocks
	CustomerServiceImpl customerService;

	@Mock
	CustomerRepository customerDAO;

	@Mock
	AccTransactionRepository accTxRepo;

	@Mock
	private ConfigProperties configProp;

	String custLogin = "";
	String txStatus = "";
	String crTxTypeCode = "";
	Customer customer;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		txStatus = TransactionStatusEnum.PROCESSED.name();
		crTxTypeCode = "CR";
		custLogin = "GAGAN001";

		MockitoAnnotations.initMocks(this);

		txStatus = TransactionStatusEnum.PROCESSED.name();
		crTxTypeCode = "CR";

		CustAccount custAccount = new CustAccount();
		custAccount.setInterestAddedDate(Timestamp.valueOf(LocalDateTime.now()));

		AccTransaction accTx = new AccTransaction();
		accTx.setTxTypeCode(crTxTypeCode);
		accTx.setCustAcct(custAccount);
		accTx.setTxStatus(txStatus);
		List<AccTransaction> accTxList = new ArrayList<>();
		accTxList.add(accTx);

		custAccount.setAccTxList(accTxList);

		List<CustAccount> custAccts = new ArrayList<>();
		custAccts.add(custAccount);

		customer = new Customer();
		customer.setActive(true);
		customer.setCustLogin(custLogin);
		customer.setCustAccts(custAccts);
		customer.setInterestRate(10);
	}

	@Test
	public void test_get_customer_acc_activities_with_valid_data() {
		when(customerDAO.findCustomerByCustLogin(custLogin)).thenReturn(customer);
		List<AccTransactionVO> accTxVOList = customerService.getCustAcctsActivities(custLogin, crTxTypeCode);
		assertNotNull(accTxVOList);
	}

	@Test
	public void test_calculate_interest_with_valid_date() {

		when(customerDAO.findCustomerByCustLogin(custLogin)).thenReturn(customer);
		//when(configProp.getInterestRate()).thenReturn(5.0);
		List<AccTransactionVO> accTxVOList = customerService.calculateInterest(custLogin);
		assertNotNull(accTxVOList);
	}

	@Test
	public void test_calculate_interest_with_valid_data_multiple_calls() {

		when(customerDAO.findCustomerByCustLogin(custLogin)).thenReturn(customer);

		List<AccTransactionVO> accTxVOList = customerService.calculateInterest(custLogin);
		assertTrue(accTxVOList.isEmpty());

		accTxVOList = customerService.calculateInterest(custLogin);
		assertTrue(accTxVOList.isEmpty());
	}
}
