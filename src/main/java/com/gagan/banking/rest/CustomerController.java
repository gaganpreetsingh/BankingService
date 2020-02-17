package com.gagan.banking.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gagan.banking.exception.ResourceNotFoundException;
import com.gagan.banking.rest.response.ResponseEntity;
import com.gagan.banking.service.CustomerService;
import com.gagan.banking.validators.CustomerValidator;
import com.gagan.banking.vo.AccTransactionVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v1/customers/")
@Api(value = "Customer Operations", tags = { "CustomerOperations" })
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@ApiOperation(value = "Get Customer's all account Transactions", tags = { "CustomerOperations" })
	@GetMapping(value = "/{custLogin}/txs/txType/{txType}")
	public ResponseEntity getCustAcctsActivities(
			@ApiParam(value = "Customer Username", required = false) @PathVariable(name = "custLogin", required = false) String custLogin,
			@ApiParam(value = "Account Transactions Type: CR or DR", required = false) @RequestParam(name = "txType", required = false) String txTypeCode) {
		ResponseEntity responseEntity = new ResponseEntity();
		try {
			CustomerValidator.validateTxType(txTypeCode);
			List<AccTransactionVO> accActivities = customerService.getCustAcctsActivities(custLogin, txTypeCode);
			responseEntity.setResult(accActivities);
		} catch (ResourceNotFoundException rs) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, rs.getMessage(), rs);
		} catch (Exception exception) {
			exception.printStackTrace();
			responseEntity.setResult(exception.getMessage());
			responseEntity.setError(true);
		}
		return responseEntity;
	}

	@ApiOperation(value = "Calculate Interest on Customer's all accounts", tags = { "CustomerOperations" })
	@GetMapping(value = "/{custLogin}/calculateInterest/")
	public ResponseEntity calculateInterest(@PathVariable("custLogin") String custLogin) {
		ResponseEntity responseEntity = new ResponseEntity();
		try {
			List<AccTransactionVO> accActvtyList = customerService.calculateInterest(custLogin);

			if (accActvtyList != null && !accActvtyList.isEmpty()) {
				responseEntity.setResult(accActvtyList);
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