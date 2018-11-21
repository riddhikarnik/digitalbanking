package com.banking.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * This class has been mapped via XML
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "accounts", uniqueConstraints = {
		@UniqueConstraint(columnNames = "accountNumber", name = "accountnumber_idx") })
public class Account extends BaseEntity {

	public Account(User user, String accountNumber, AccountType accountType, Currency currency, BigDecimal balance,
			boolean enable, Date createdOn, Date updatedOn) {
		super();
		this.user = user;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.currency = currency;
		this.balance = balance;
		this.enabled = enable;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@Column(name = "accountnumber")
	private String accountNumber;

	@Column(name = "accounttype")
	@Enumerated(EnumType.STRING)
	private AccountType accountType;

	@Column(name = "localcurrency")
	@Enumerated(EnumType.STRING)
	private Currency currency;

	private BigDecimal balance;

	private boolean enabled = true;

	@Column(name = "creationtimestamp")
	private Date createdOn;

	@Column(name = "updationtimestamp")
	private Date updatedOn;

	public Account() {
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enable) {
		this.enabled = enable;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "Account [user=" + user + ", accountNumber=" + accountNumber + ", accountType=" + accountType
				+ ", currency=" + currency + ", enabled=" + enabled + ", createdOn=" + createdOn + ", updatedOn="
				+ updatedOn + "]";
	}
}
