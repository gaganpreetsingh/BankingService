package com.gagan.banking.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the tb_cust_acct database table.
 * 
 */
@Entity
@Table(name = "tb_cust_acct")
@NamedQuery(name = "CustAccount.fintAccByNum", query = "SELECT custAcc FROM CustAccount custAcc WHERE custAcc.accNo= :accNo")
public class CustAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "acc_id")
	private int accId;

	@Column(name = "acc_balance")
	private double accBalance;

	@Column(name = "acc_no")
	private String accNo;

	@Column(name = "acc_typ")
	private String accTyp;

	private boolean active;

	@Column(name = "int_added_date")
	private Timestamp interestAddedDate;

	public Timestamp getInterestAddedDate() {
		return interestAddedDate;
	}

	public void setInterestAddedDate(Timestamp interestAddedDate) {
		this.interestAddedDate = interestAddedDate;
	}

	// bi-directional many-to-one association to AccTransaction
	@OneToMany(mappedBy = "custAcct")
	@JsonIgnore
	private List<AccTransaction> accTxList;

	// bi-directional many-to-one association to TbBnkCust
	@ManyToOne
	@JoinColumn(name = "cust_id")
	private Customer tbBnkCust;

	public CustAccount() {
	}

	public int getAccId() {
		return this.accId;
	}

	public void setAccId(int accId) {
		this.accId = accId;
	}

	public double getAccBalance() {
		return this.accBalance;
	}

	public void setAccBalance(double accBalance) {
		this.accBalance = accBalance;
	}

	public String getAccNo() {
		return this.accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getAccTyp() {
		return this.accTyp;
	}

	public void setAccTyp(String accTyp) {
		this.accTyp = accTyp;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<AccTransaction> getAccTxList() {
		return this.accTxList;
	}

	public void setAccTxList(List<AccTransaction> accTxList) {
		this.accTxList = accTxList;
	}

	public Customer getTbBnkCust() {
		return this.tbBnkCust;
	}

	public void setTbBnkCust(Customer tbBnkCust) {
		this.tbBnkCust = tbBnkCust;
	}

}