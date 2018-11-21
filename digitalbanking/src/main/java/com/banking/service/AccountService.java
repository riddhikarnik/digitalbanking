package com.banking.service;

import java.math.BigDecimal;
import java.util.List;

import com.banking.entity.Account;
import com.banking.entity.User;
import com.banking.util.exceptions.AccountNotFoundException;

public interface AccountService {

	List<Account> getAll(User user);

	Account get(int accountId) throws AccountNotFoundException;

	Account get(String accountNumber);

	Account save(Account account, int userId);

	Account update(Account account);

	Account convert(com.banking.rest.request.Account account);

	void deposit(BigDecimal amount, int accountId) throws AccountNotFoundException;

	void withdraw(BigDecimal amount, int accountId) throws AccountNotFoundException;

}
