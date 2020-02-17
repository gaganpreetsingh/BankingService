package com.gagan.banking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gagan.banking.vo.AccTransactionVO;

@Service
public interface CustomerService {

	public List<AccTransactionVO> getCustAcctsActivities(String custLogin, String accActCode);

	public List<AccTransactionVO> calculateInterest(String custLogin);
}
