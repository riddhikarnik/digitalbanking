package com.banking.entity.manager;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.entity.Account;
import com.banking.entity.User;
import com.banking.entity.repository.AccountRepository;
import com.banking.util.exceptions.AccountNotFoundException;

@Service
public class AccountManager {

	private static final Logger logger = Logger.getLogger(AccountManager.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private AccountRepository accountRepository;

	@Transactional(readOnly = false)
	public Account save(Account account, int userId) {

		User user = new User();
		user.setId(userId);
		account.setUser(user);

		account.setCreatedOn(new Date());
		logger.info("Creating new entity: " + account);
		return accountRepository.saveAndFlush(account);
	}

	@Transactional
	public Account update(Account account) {
		logger.info("Updating existing entity");
		account.setUpdatedOn(new Date());
		return accountRepository.saveAndFlush(account);
	}

	/**
	 * Using the Query Annotation
	 */
	public List<Account> getAccounts(int userId) {
		logger.info("Fetching accounts for userId " + userId);
		return accountRepository.findAccounts(userId);
	}

	public Account getAccount(int id) throws AccountNotFoundException {
		logger.info("Fetching account with id " + id);
		Account accountById = accountRepository.findById(id);

		if (accountById == null) {
			throw new AccountNotFoundException();
		}

		if (accountById.getUser() == null) {
			logger.info("User not retrieved");
		}

		return accountById;
	}

	/**
	 * Using a Named Query
	 */
	public Account getAccount(String accountNumber) {
		logger.info("Fetching account with number " + accountNumber);
		return entityManager.createNamedQuery(GET_BY_NUMBER, Account.class).setParameter("accountNumber", accountNumber)
				.getSingleResult();
	}

	private static final String GET_BY_NUMBER = "SELECT a FROM Account a WHERE a.accountNumber = :accountNumber";

}