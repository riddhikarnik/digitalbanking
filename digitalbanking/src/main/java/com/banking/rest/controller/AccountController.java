package com.banking.rest.controller;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.banking.entity.Account;
import com.banking.rest.constants.RestURIConstants;
import com.banking.rest.request.TransactionDetails;
import com.banking.service.AccountService;
import com.banking.util.AccountUtil;
import com.banking.util.exceptions.AccountNotFoundException;
import com.banking.util.exceptions.InvalidInputException;

@RestController
public class AccountController extends BaseController {

	private static final Logger logger = Logger.getLogger(AccountController.class);

	@Autowired
	AccountService accountService;

	@RequestMapping(value = RestURIConstants.ACCOUNT.CREATE, method = RequestMethod.POST)
	public @ResponseBody Account createAccount(@RequestBody com.banking.rest.request.Account account)
			throws InvalidInputException, AccountNotFoundException {

		logger.info("begin_createAccount.");
		Account savedAccount = accountService.save(accountService.convert(account), getLoggedInUserId());
		return accountService.get(savedAccount.getId());
	}

	/**
	 * URI with a Path Variable
	 */
	@RequestMapping(value = RestURIConstants.ACCOUNT.RETRIEVE, method = RequestMethod.POST)
	public @ResponseBody Account retrieveAccountDetails(@PathVariable("id") int accountId)
			throws InvalidInputException, AccountNotFoundException {

		logger.info("begin_retrieveAccountDetails " + accountId);
		return accountService.get(accountId);
	}

	/**
	 * URI with multiple Path Variables
	 */

	@RequestMapping(value = RestURIConstants.ACCOUNT.DEPOSIT, method = RequestMethod.POST)
	public @ResponseBody Account deposit(@PathVariable("id") int accountId, @PathVariable("amount") String amount)
			throws InvalidInputException, AccountNotFoundException {

		logger.info("begin_deposit " + amount + " in account " + accountId);

		BigDecimal newBalance = new BigDecimal(amount);

		if (AccountUtil.isPositive(newBalance)) {
			accountService.deposit(newBalance, accountId);
			return this.accountService.get(accountId);
		}
		throw new InvalidInputException("Please input an amount greater than 0.");
	}

	@RequestMapping(value = RestURIConstants.ACCOUNT.DEDUCT, method = RequestMethod.POST)
	public @ResponseBody Account deduct(@RequestBody TransactionDetails transaction)
			throws InvalidInputException, AccountNotFoundException {

		logger.info("begin_deduction " + transaction);

		if (AccountUtil.isPositive(transaction.getAmount())) {
			accountService.withdraw(transaction.getAmount(), transaction.getAccountId());
			return this.accountService.get(transaction.getAccountId());
		}
		throw new InvalidInputException("Please input an amount greater than 0.");
	}

}
