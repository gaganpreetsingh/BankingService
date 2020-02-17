package com.gagan.banking.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.gagan.banking.config.ConfigProperties;
import com.gagan.banking.constant.Constants;
import com.gagan.banking.dao.AccTransactionRepository;
import com.gagan.banking.dao.AccountRepository;
import com.gagan.banking.dao.CustomerRepository;
import com.gagan.banking.exception.ResourceNotFoundException;
import com.gagan.banking.model.AccTransaction;
import com.gagan.banking.model.TransactionType;
import com.gagan.banking.model.CustAccount;
import com.gagan.banking.model.Customer;
import com.gagan.banking.model.TransactionStatusEnum;
import com.gagan.banking.service.CustomerService;
import com.gagan.banking.util.BankUtility;
import com.gagan.banking.vo.AccTransactionVO;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	CustomerRepository customerDAO;

	@Autowired
	AccountRepository accDAO;

	@Autowired
	private AccTransactionRepository accTxRepo;

	@Autowired
	private ConfigProperties configProperties;

	@Override
	public List<AccTransactionVO> getCustAcctsActivities(String custLogin, String accActCode) {
		LOGGER.debug("getCustomerAllAccsActivities -- Started");
		List<AccTransaction> list = null;

		List<AccTransactionVO> accTxVOList = new ArrayList<>();

		LOGGER.debug("Fetching all Accounts activities of requested Customer..");
		Customer cust = customerDAO.findCustomerByCustLogin(custLogin);

		if (cust != null && cust.getCustAccts() != null) {
			list = cust.getCustAccts().stream().flatMap(custAcc -> custAcc.getAccTxList().stream())
					.collect(Collectors.toList());
		} else {
			throw new ResourceNotFoundException(Constants.RESOURCE_DOESN_T_EXIST);
		}

		if (!StringUtils.isEmpty(accActCode)) {
			LOGGER.debug("Filtering AccountActivities as per User request and show only " + accActCode + " entries");
			list = list.stream()
					.filter(accAct -> accAct.getTxTypeCode().equals(TransactionType.valueOf(accActCode).name()))
					.collect(Collectors.toList());
		}
		LOGGER.debug("Customer's All Account Activities Size " + list.size());
		LOGGER.debug("getCustomerAllAccsActivities -- Ended");

		list.forEach(acc -> {
			accTxVOList.add(BankUtility.populateAccTxVO(acc));
		});
		return accTxVOList;
	}

	@Override
	public List<AccTransactionVO> calculateInterest(String custLogin) {
		LOGGER.debug("calculateInterest -- Started");
		Customer customer = customerDAO.findCustomerByCustLogin(custLogin);
		double interestRate = customer.getInterestRate() == -1 ? configProperties.getInterestRate()
				: customer.getInterestRate();
		List<AccTransaction> accTxList = new ArrayList<>();
		List<AccTransactionVO> accTxVOList = new ArrayList<>();

		if (customer != null && customer.isActive()) {
			LocalDateTime localDateTime = LocalDateTime.now();
			List<CustAccount> accList = customer.getCustAccts();
			LOGGER.info("Number of accounts found with this customer: " + accList.size());
			accList.stream().filter(acc -> {
				boolean flag = false;
				boolean isInterestCalculatedForThisPeriod = acc.getInterestAddedDate().toLocalDateTime().toLocalDate()
						.equals(localDateTime.toLocalDate());
				flag = acc.isActive() && !isInterestCalculatedForThisPeriod;
				LOGGER.debug("Account : " + acc.getAccNo() + " is " + acc.isActive());
				LOGGER.debug("Interest Calculated for this period: " + isInterestCalculatedForThisPeriod);
				return flag;
			}).map(acc -> {
				Timestamp now = Timestamp.valueOf(localDateTime);
				// Get Interest Rate from Customer if its -1 then get it from
				// Application Level
				LOGGER.debug("Interest Rate for Customer : " + interestRate);
				double interestAmt = BankUtility.round(acc.getAccBalance() * interestRate / 100);
				double balance = BankUtility.round(acc.getAccBalance() + interestAmt);
				acc.setAccBalance(balance);
				acc.setInterestAddedDate(now);
				AccTransaction accTx = new AccTransaction();
				accTx.setTxTypeCode(TransactionType.CR.name());
				accTx.setAmountAffected(interestAmt);
				accTx.setCreatedOn(now);
				accTx.setMessage("Interest Credited");
				accTx.setTxStatus(TransactionStatusEnum.PROCESSED.name());
				accTx.setCustAcct(acc);
				accTx.setTxRef("i-" + BankUtility.numbGen());
				accTx.setBalance(balance);
				accTxList.add(accTx);
				LOGGER.debug("Added Transaction entry for A/c: " + accTx.getTxRef());
				return acc;
			}).collect(Collectors.toList());
			accTxRepo.saveAll(accTxList);
			LOGGER.info("calculateInterest -- Ended");
			accTxList.forEach(acc -> {
				accTxVOList.add(BankUtility.populateAccTxVO(acc));
			});
			return accTxVOList;
		} else {
			throw new ResourceNotFoundException(Constants.RESOURCE_DOESN_T_EXIST);
		}
	}
}
