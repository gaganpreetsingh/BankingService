package com.gagan.banking.model;

public enum TransactionStatusEnum {
	PROCESSED("Processed"), FAIL("FAILED"), HOLD("Hold"), CANCELLED("Cancelled"), INPRG("In Progress");

	String name;

	TransactionStatusEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
