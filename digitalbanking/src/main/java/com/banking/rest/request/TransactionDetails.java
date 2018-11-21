package com.banking.rest.request;

import java.math.BigDecimal;

public class TransactionDetails {

	@Override
	public String toString() {
		return "TransactionDetails [accountId=" + accountId + ", amount=" + amount + ", comments=" + comments + "]";
	}

	Integer accountId;
	BigDecimal amount;
	String comments;

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
