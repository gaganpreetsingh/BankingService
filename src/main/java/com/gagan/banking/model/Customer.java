package com.gagan.banking.model;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The persistent class for the tb_bnk_cust database table.
 * 
 */
@Component
@Entity
@Table(name = "tb_bnk_cust")
@NamedQuery(name = "Customer.fintCustomerIDByLogin", query = "SELECT cust FROM Customer cust WHERE cust.custLogin= :custLogin")
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cust_id")
	private int custId;

	private boolean active;

	@Column(name = "cust_fname")
	private String custFname;

	@Column(name = "cust_login")
	private String custLogin;

	@Column(name = "cust_lname")
	private String custLname;

	private String email;

	private double interestRate;

	@OneToMany(mappedBy = "tbBnkCust")
	private List<CustAccount> tbCustAccts;

	public Customer() {
	}

	public int getCustId() {
		return this.custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCustFname() {
		return this.custFname;
	}

	public void setCustFname(String custFname) {
		this.custFname = custFname;
	}

	public String getCustLname() {
		return this.custLname;
	}

	public void setCustLname(String custLname) {
		this.custLname = custLname;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<CustAccount> getCustAccts() {
		return this.tbCustAccts;
	}

	public void setCustAccts(List<CustAccount> tbCustAccts) {
		this.tbCustAccts = tbCustAccts;
	}

	public String getCustLogin() {
		return custLogin;
	}

	public void setCustLogin(String custLogin) {
		this.custLogin = custLogin;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	@Override
	public String toString() {
		return "Customer [custId=" + custId + ", active=" + active + ", custFname=" + custFname + ", custLogin="
				+ custLogin + ", custLname=" + custLname + ", email=" + email + "]";
	}

}