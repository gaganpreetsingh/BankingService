package com.gagan.banking.vo;

import java.sql.Timestamp;

public class AccTransactionVO {

	private double amountAffected;
	private Timestamp createdOn;
	private String message;
	private String txStatus;
	private String txId;
	private String accNo;
	private String txTypeName;

	private double affectedBal;

	public String getTxTypeName() {
		return txTypeName;
	}

	public void setTxTypeName(String txTypeName) {
		this.txTypeName = txTypeName;
	}

	public double getAmountAffected() {
		return amountAffected;
	}

	public void setAmountAffected(double amountAffected) {
		this.amountAffected = amountAffected;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTxStatus() {
		return txStatus;
	}

	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public double getAffectedBal() {
		return affectedBal;
	}

	public void setAffectedBal(double affectedBal) {
		this.affectedBal = affectedBal;
	}
}