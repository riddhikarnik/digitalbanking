package com.banking.util;

import java.math.BigDecimal;
import java.util.UUID;

import com.banking.entity.AccountType;

public class AccountUtil {

	private AccountUtil() {
	}

	public static String createAccountNumber(String accountType) {
		AccountType type = AccountType.valueOf(accountType);

		String prefix = "";

		switch (type) {
		case CHEQUING: {
			prefix = "1111";
			break;
		}
		case SAVINGS: {
			prefix = "2222";
			break;
		}
		case BILLING: {
			prefix = "9999";
			break;
		}
		}

		StringBuilder sb = new StringBuilder(prefix);
		sb.append(UUID.randomUUID());
		// TODO sb.append(getAccountNumberSequentially());
		return sb.toString();
	}

	public static boolean isPositive(BigDecimal amount) {
		return (amount.compareTo(BigDecimal.ZERO) == 1);

	}

	public static boolean isNegative(BigDecimal amount) {
		return (amount.compareTo(BigDecimal.ZERO) == -1);

	}

}
