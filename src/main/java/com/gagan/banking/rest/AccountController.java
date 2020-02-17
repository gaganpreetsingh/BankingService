package com.gagan.banking.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gagan.banking.exception.ResourceNotFoundException;
import com.gagan.banking.rest.response.ResponseEntity;
import com.gagan.banking.service.AccountService;
import com.gagan.banking.validators.CustomerValidator;
import com.gagan.banking.vo.AccTransactionVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v1/accounts/*")
@Api(value = "Account Operations", tags = { "AccountOperation" })
public class AccountController {

	@Autowired
	AccountService accountService;

	@ApiOperation(value = "Get Account Balance", tags = { "AccountOperation" })
	@GetMapping(value = "/{accNo}/checkBalance")
	public ResponseEntity getBalance(@PathVariable("accNo") String accNo) {
		ResponseEntity responseEntity = new ResponseEntity();
		try {
			responseEntity.setResult(accountService.getBalance(accNo));
		} catch (ResourceNotFoundException rs) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, rs.getMessage(), rs);
		} catch (Exception exception) {
			responseEntity.setResult(exception.getMessage());
			responseEntity.setError(true);
		}
		return responseEntity;
	}

	@ApiOperation(value = "Get Transactions", tags = { "AccountOperation" })
	@GetMapping(value = "/{accNo}/txs/txType/{txType}")
	public ResponseEntity getAccTransactions(
			@ApiParam(value = "Account Number", required = false) @PathVariable("accNo") String accNo,
			@ApiParam(value = "Transaction Type: CR or DR or All", required = false) @PathVariable("txType") String txTypeCode) {
		ResponseEntity responseEntity = new ResponseEntity();
		try {
			CustomerValidator.validateTxType(txTypeCode);

			List<AccTransactionVO> transactions = accountService.getTransactions(accNo, txTypeCode);
			responseEntity.setResult(transactions);
		} catch (ResourceNotFoundException rs) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, rs.getMessage(), rs);
		} catch (Exception exception) {
			exception.printStackTrace();
			responseEntity.setResult(exception.getMessage());
			responseEntity.setError(true);
		}
		return responseEntity;
	}

	@ApiOperation(value = "Calculate Interest on account", tags = { "AccountOperation" })
	@GetMapping(value = "/{accNo}/calculateInterest/")
	public ResponseEntity calculateInterest(@PathVariable("accNo") String accNo) {
		ResponseEntity responseEntity = new ResponseEntity();
		try {
			AccTransactionVO accTxVO = accountService.calculateInterest(accNo);

			if (accTxVO != null) {
				responseEntity.setResult(accTxVO);
			} else {
				responseEntity.setResult("Interest is already calculated for certain period or A/c is inactive.");
			}
		} catch (ResourceNotFoundException rs) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, rs.getMessage(), rs);
		} catch (Exception exception) {
			exception.printStackTrace();
			responseEntity.setResult(exception.getMessage());
			responseEntity.setError(true);
		}
		return responseEntity;
	}
}