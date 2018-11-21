package com.banking.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.entity.Account;
import com.banking.entity.AccountType;
import com.banking.entity.Currency;
import com.banking.entity.TransactionStatus;
import com.banking.entity.TransactionType;
import com.banking.entity.User;
import com.banking.entity.manager.AccountManager;
import com.banking.util.AccountUtil;
import com.banking.util.exceptions.AccountNotActiveException;
import com.banking.util.exceptions.AccountNotFoundException;
import com.banking.util.exceptions.InsufficientFundsException;

@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger logger = Logger.getLogger(AccountServiceImpl.class);

	@Autowired
	private AccountManager accountManager;

	@Override
	public List<Account> getAll(User user) {
		logger.info("Retrieving accounts for " + user.getLoginId());
		return accountManager.getAccounts(user.getId());
	}

	@Override
	public Account get(int accountId) throws AccountNotFoundException {
		logger.info("Retrieving accounts for " + accountId);
		return accountManager.getAccount(accountId);
	}

	@Override
	public Account get(String accountNumber) {
		logger.info("Retrieving accounts for " + accountNumber);
		return accountManager.getAccount(accountNumber);
	}

	@Override
	public Account save(Account account, int userId) {

		logger.info("Saving entity account " + account);

		return accountManager.save(account, userId);
	}

	@Override
	public Account update(Account account) {
		logger.info("Updating entity account " + account);
		return accountManager.update(account);
	}

	@Override
	public Account convert(com.banking.rest.request.Account account) {
		logger.info("Convert started for " + account);
		Account acc = new Account();

		acc.setAccountNumber(AccountUtil.createAccountNumber(account.getAccountType()));
		acc.setBalance(account.getOpeningBalance());
		acc.setAccountType(AccountType.valueOf(account.getAccountType()));
		acc.setCurrency(Currency.valueOf(account.getPreferredCurrency()));

		logger.info("Converted and returing " + acc);
		return acc;

	}

	@Override
	public void deposit(BigDecimal amount, int accountId) throws AccountNotFoundException {

		Account account = get(accountId);
		if (account.isEnabled()) {
			account.setBalance(account.getBalance().add(amount));
		} else {
			throw new AccountNotActiveException();
		}

		logTransaction(account, TransactionType.DEPOSIT, TransactionStatus.INIT);

		logger.info("Updating Balance for " + accountId);
		Account updatedAccount = update(account);

		logTransaction(updatedAccount, TransactionType.DEPOSIT, TransactionStatus.COMPLETED);
	}

	@Override
	public void withdraw(BigDecimal amount, int accountId) throws AccountNotFoundException {

		Account account = get(accountId);
		if (!account.isEnabled()) {
			throw new AccountNotActiveException();
		}
		BigDecimal deductedBalance = account.getBalance().subtract(amount);

		if (AccountUtil.isNegative(deductedBalance)) {
			throw new InsufficientFundsException();
		}

		account.setBalance(deductedBalance);

		logTransaction(account, TransactionType.WITHDRAW, TransactionStatus.INIT);

		logger.info("Updating Balance for " + accountId);
		Account updatedAccount = update(account);

		logTransaction(updatedAccount, TransactionType.WITHDRAW, TransactionStatus.COMPLETED);
	}

	private void logTransaction(Account updatedAccount, TransactionType transactionType, String status) {
		// call to a TaskExecutor or Aspect based method
	}
}
