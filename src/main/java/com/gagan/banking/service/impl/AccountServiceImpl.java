package com.gagan.banking.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import com.gagan.banking.exception.ResourceNotFoundException;
import com.gagan.banking.model.AccTransaction;
import com.gagan.banking.model.TransactionType;
import com.gagan.banking.model.CustAccount;
import com.gagan.banking.model.Customer;
import com.gagan.banking.model.TransactionStatusEnum;
import com.gagan.banking.service.AccountService;
import com.gagan.banking.util.BankUtility;
import com.gagan.banking.vo.AccTransactionVO;

@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	ConfigProperties configProp;

	@Autowired
	AccountRepository accRepo;

	@Autowired
	AccTransactionRepository accTxRepo;

	@Override
	public double getBalance(String accNo) {
		Optional<CustAccount> custAcctOpt = accRepo.findByAccNo(accNo);
		if (custAcctOpt.isPresent()) {
			return custAcctOpt.get().getAccBalance();
		} else {
			throw new ResourceNotFoundException(Constants.RESOURCE_DOESN_T_EXIST);
		}
	}

	@Override
	public List<AccTransactionVO> getTransactions(String accNo, String txType) {
		LOGGER.debug("getTransactions -- Started");
		List<AccTransaction> list = null;
		List<AccTransactionVO> accTxVOList = new ArrayList<>();
		if (!StringUtils.isEmpty(accNo)) {
			LOGGER.debug("Fetching the Account transactions as per given Account.");
			Optional<CustAccount> custAcctOpt = accRepo.findByAccNo(accNo);
			if (custAcctOpt.isPresent()) {
				list = custAcctOpt.get().getAccTxList();
			} else {
				throw new ResourceNotFoundException("Acc Number doesn't exist!!!");
			}
		}
		if (!StringUtils.isEmpty(txType) && !Constants.ALL.equalsIgnoreCase(txType)) {
			list = list.stream()
					.filter(accAct -> accAct.getTxTypeCode().equals(TransactionType.valueOf(txType).name()))
					.collect(Collectors.toList());
		}
		LOGGER.debug("Account Transactions Size " + list.size());
		LOGGER.debug("getTransactions -- Ended");

		list.forEach(acc -> {
			accTxVOList.add(BankUtility.populateAccTxVO(acc));
		});
		return accTxVOList;
	}

	@Override
	public AccTransactionVO calculateInterest(String accNo) {
		LOGGER.debug("calculateInterest -- Started");
		Optional<CustAccount> optionalAcc = accRepo.findByAccNo(accNo);

		double interestRate = configProp.getInterestRate();
		LOGGER.debug("Interest Rate : " + interestRate);
		AccTransaction accTx = null;
		AccTransactionVO accTxVO = null;

		CustAccount account = null;

		if (optionalAcc.isPresent()) {
			account = optionalAcc.get();
			LocalDateTime localDateTime = LocalDateTime.now();
			boolean isInterestCalculatedForThisPeriod = account.getInterestAddedDate().toLocalDateTime().toLocalDate()
					.equals(localDateTime.toLocalDate());

			Customer customer = account.getTbBnkCust();

			// Get Interest Rate from Customer if its -1 then get it from
			// Application Level
			interestRate = customer.getInterestRate() == -1 ? interestRate : customer.getInterestRate();

			if (account.isActive() && account.getTbBnkCust().isActive() && !isInterestCalculatedForThisPeriod) {
				LOGGER.debug("Account : " + account.getAccNo() + " is " + account.isActive());
				LOGGER.debug("Interest Calculated for this period: " + isInterestCalculatedForThisPeriod);
				Timestamp now = Timestamp.valueOf(localDateTime);
				double interestAmt = BankUtility.round(account.getAccBalance() * interestRate / 100);
				double balance = BankUtility.round(account.getAccBalance() + interestAmt);
				account.setAccBalance(balance);
				account.setInterestAddedDate(now);
				accTx = new AccTransaction();
				accTx.setTxTypeCode(TransactionType.CR.name());
				accTx.setAmountAffected(interestAmt);
				accTx.setCreatedOn(now);
				accTx.setMessage("Interest Credited");
				accTx.setTxStatus(TransactionStatusEnum.PROCESSED.name());
				accTx.setCustAcct(account);
				accTx.setTxRef("i-" + BankUtility.numbGen());
				accTx.setBalance(balance);
				LOGGER.debug("Added Transaction entry for A/c: " + accTx.getTxRef());
				accTxRepo.save(accTx);
			}
		} else {
			LOGGER.info("calculateInterest - Account doesn't exist");
			throw new ResourceNotFoundException(Constants.RESOURCE_DOESN_T_EXIST);
		}
		LOGGER.info("calculateInterest -- Ended");
		accTxVO = BankUtility.populateAccTxVO(accTx);
		return accTxVO;
	}
}