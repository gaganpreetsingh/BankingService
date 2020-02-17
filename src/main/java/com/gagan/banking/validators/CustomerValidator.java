package com.gagan.banking.validators;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.gagan.banking.exception.ResourceNotFoundException;
import com.gagan.banking.model.TransactionType;

public class CustomerValidator {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerValidator.class);

	public static boolean validateTxType(String accActTypeCode) {
		LOGGER.debug("checkAccActyTypeValid - Started..");
		boolean flag = false;
		if (StringUtils.isEmpty(accActTypeCode)) {
			flag = true;
		} else {
			if (Stream.of(TransactionType.values()).filter(acc -> acc.name().equalsIgnoreCase(accActTypeCode))
					.count() > 0) {
				LOGGER.debug("Transaction does exist");
				return true;
			} else {
				LOGGER.debug("Invaid Transaction Type : " + accActTypeCode);
				throw new ResourceNotFoundException("Resource doesn't exist");
			}
		}
		LOGGER.debug("checkAccActyTypeValid - Ended..");
		return flag;
	}
}
