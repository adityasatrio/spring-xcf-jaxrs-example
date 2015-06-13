package com.example.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.hateoas.ResourceSupport;

@Entity
@Table(name = "account")
public class Account implements Serializable {

	private Long id;
	private String accountNo;
	private BigDecimal balance;
	private Date lastPaidOn;
	private Contact contact;

	public Account() {
		// TODO Auto-generated constructor stub
	}

	public Account(String accountNo) {
		this.accountNo = accountNo;
	}

	public Account(Long id, String accountNo, BigDecimal balance, Date lastPaidOn) {
		this.id = id;
		this.accountNo = accountNo;
		this.balance = balance;
		this.lastPaidOn = lastPaidOn;
	}
	
	public Account(String accountNo, BigDecimal balance, Date lastPaidOn) {
		this.accountNo = accountNo;
		this.balance = balance;
		this.lastPaidOn = lastPaidOn;
	}

	@Id
	@Column(name= "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "account_no")
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	@Column
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "last_paid_on")
	public Date getLastPaidOn() {
		return lastPaidOn;
	}

	public void setLastPaidOn(Date lastPaidOn) {
		this.lastPaidOn = lastPaidOn;
	}

	@JoinColumn(name = "contact_id")
	@ManyToOne(fetch = FetchType.LAZY)
	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", accountNo=" + accountNo + ", balance="
				+ balance + ", lastPaidOn=" + lastPaidOn + ", contact=["
				+ contact.getEmail()+ "," + contact.getFullName() + ","
				+ contact.getFirstName() + ","+ contact.getLastName() + ","
				+ contact.getMiddleInitial() + "] ]";
	}

}
