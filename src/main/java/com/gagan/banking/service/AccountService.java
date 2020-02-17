package com.gagan.banking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gagan.banking.vo.AccTransactionVO;

@Service
public interface AccountService {

	public double getBalance(String accNo);

	public List<AccTransactionVO> getTransactions(String accNo, String accActCode);

	public AccTransactionVO calculateInterest(String custLogin);
}
