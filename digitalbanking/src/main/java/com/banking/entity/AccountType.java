package com.banking.entity;

import java.io.Serializable;

public enum AccountType implements Serializable {

	CHEQUING, SAVINGS, BILLING;

	public String getType() {
		return name();
	}
}
