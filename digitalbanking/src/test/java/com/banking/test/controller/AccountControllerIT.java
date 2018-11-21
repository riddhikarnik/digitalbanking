package com.banking.test.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.banking.entity.Account;
import com.banking.entity.AccountType;
import com.banking.entity.Currency;
import com.banking.rest.controller.AccountController;
import com.banking.service.AccountService;
import com.banking.test.InitContext;

public class AccountControllerIT extends InitContext {

	private static final String TEST_ACCOUNT_TYPE = AccountType.CHEQUING.name();

	@MockBean
	private AccountService accountService;

	@MockBean
	private AccountController accountController;

	@Test
	public void testCreateAccount() throws Exception {

		when(accountController.getLoggedInUserId()).thenReturn(getMockUserId());
		when(accountService.convert(Mockito.any(com.banking.rest.request.Account.class))).thenReturn(getMockAccount());
		when(accountService.save(getMockAccount(), getMockUserId())).thenReturn(getMockAccount());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/account/create")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(getRequestJSON())
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(jsonPath("$.token").exists());

	}

	private String getRequestJSON() {
		return "{\"accountType\": \"" + TEST_ACCOUNT_TYPE + "\",\n" + "   \"openingBalance\" : 100.00 ,"
				+ "\"preferredCurrency\": \"CAD\" }";
	}

	private int getMockUserId() {
		return 1;
	}

	private Account getMockAccount() {
		Account account = new Account();
		account.setAccountType(AccountType.valueOf(TEST_ACCOUNT_TYPE));
		account.setBalance(new BigDecimal(100.00));
		account.setCurrency(Currency.CAD);
		return account;
	}
}
