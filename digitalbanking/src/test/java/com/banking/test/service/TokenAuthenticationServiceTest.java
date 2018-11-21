package com.banking.test.service;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.banking.entity.User;
import com.banking.service.UserService;
import com.banking.service.UserServiceImpl;
import com.banking.services.authentication.TokenService;
import com.banking.services.authentication.impl.JWTTokenService;
import com.banking.services.authentication.impl.TokenAuthenticationService;
import com.banking.test.InitContext;
import com.banking.util.PasswordUtil;

@RunWith(SpringJUnit4ClassRunner.class)
public class TokenAuthenticationServiceTest extends InitContext {

	private static final String SOME_TOKEN_VALUE = "someTokenValue";

	private static final String DUMMY_PASSWORD = "dummyPassword";

	private static final String DUMMY_USER = "dummyUser";

	TokenService tokenService = Mockito.mock(JWTTokenService.class);

	UserService userService = Mockito.mock(UserServiceImpl.class);

	TokenAuthenticationService tokenAuthenticationService = new TokenAuthenticationService(tokenService, userService);

	@Test
	public void testlogin_expectSuccess() {

		User dummyUser = new User();
		dummyUser.setLoginId(DUMMY_USER);
		dummyUser.setPassword(PasswordUtil.encode(DUMMY_PASSWORD));

		when(userService.getByLogin(ArgumentMatchers.anyString())).thenReturn(dummyUser);
		when(tokenService.expiring(ArgumentMatchers.anyMap())).thenReturn(SOME_TOKEN_VALUE);

		when(userService.getByLogin(DUMMY_USER)).thenReturn(dummyUser);

		Optional<String> result = tokenAuthenticationService.login(DUMMY_USER, DUMMY_PASSWORD);

		Mockito.verify(userService, times(1)).getByLogin(ArgumentMatchers.anyString());
		Mockito.verify(tokenService, times(1)).expiring(ArgumentMatchers.anyMap());
		assert (result.isPresent());
		assert (result.get().equals(SOME_TOKEN_VALUE));

	}

	@Test
	public void testlogin_incorrectPassword() {

		User dummyUser = new User();
		dummyUser.setLoginId(DUMMY_USER);
		dummyUser.setPassword(PasswordUtil.encode(DUMMY_PASSWORD));

		when(userService.getByLogin(ArgumentMatchers.anyString())).thenReturn(dummyUser);
		when(tokenService.expiring(ArgumentMatchers.anyMap())).thenReturn(SOME_TOKEN_VALUE);

		when(userService.getByLogin(DUMMY_USER)).thenReturn(dummyUser);

		Optional<String> result = tokenAuthenticationService.login(DUMMY_USER, "Wrong password");

		Mockito.verify(userService, times(1)).getByLogin(ArgumentMatchers.anyString());
		Mockito.verify(tokenService, times(0)).expiring(ArgumentMatchers.anyMap());
		assertFalse(result.isPresent());

	}

	@Test
	public void testlogin_incorrectUser() {

		when(userService.getByLogin(Mockito.anyString())).thenReturn(null);

		Optional<String> result = tokenAuthenticationService.login(DUMMY_USER, DUMMY_PASSWORD);
		// PowerMockito.verifyNoMoreInteractions(PasswordUtil.class);

		Mockito.verify(userService, times(1)).getByLogin(ArgumentMatchers.anyString());
		Mockito.verify(tokenService, times(0)).expiring(ArgumentMatchers.anyMap());
		assertFalse(result.isPresent());

	}
}