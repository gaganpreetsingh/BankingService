package com.gagan.banking.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the ACC_TX database table.
 * 
 */
@Component
@Entity
@Table(name = "ACC_TX")
@NamedQuery(name = "AccTransaction.findAll", query = "SELECT a FROM AccTransaction a")
public class AccTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "acc_tx_id")
	private int accTxID;

	@Column(name = "amount_affected")
	private double amountAffected;

	@Column(name = "created_on")
	private Timestamp createdOn;

	private String message;

	private String txStatus;

	@Column(name = "tx_ref")
	private String txRef;

	@ManyToOne
	@JoinColumn(name = "acc_id")
	@JsonIgnore
	private CustAccount custAcct;

	@Column(name = "tx_type_code")
	private String txTypeCode;

	@Column(name = "balance")
	private double balance;

	public int getAccTxID() {
		return accTxID;
	}

	public void setAccTxID(int accTxID) {
		this.accTxID = accTxID;
	}

	public CustAccount getCustAcct() {
		return custAcct;
	}

	public void setCustAcct(CustAccount custAcct) {
		this.custAcct = custAcct;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public AccTransaction() {
	}

	public int getAccActvtyId() {
		return this.accTxID;
	}

	public void setAccActvtyId(int accActvtyId) {
		this.accTxID = accActvtyId;
	}

	public double getAmountAffected() {
		return this.amountAffected;
	}

	public void setAmountAffected(double amountAffected) {
		this.amountAffected = amountAffected;
	}

	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getMessage() {
		return this.message;
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

	public String getTxRef() {
		return txRef;
	}

	public void setTxRef(String txRef) {
		this.txRef = txRef;
	}

	public String getTxTypeCode() {
		return txTypeCode;
	}

	public void setTxTypeCode(String txTypeCode) {
		this.txTypeCode = txTypeCode;
	}

}