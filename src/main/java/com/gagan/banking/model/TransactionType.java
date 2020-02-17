package com.gagan.banking.model;

/**
 * This class defines Transaction Type
 */
public enum TransactionType {

	DR("Debit"), CR("Credit"), ALL("All");

	private String txTypeName;

	private TransactionType(String txTypeName) {
		this.txTypeName = txTypeName;
	}

	public String getTxTypeName() {
		return txTypeName;
	}

	public void setTxTypeName(String txTypeName) {
		this.txTypeName = txTypeName;
	}

}