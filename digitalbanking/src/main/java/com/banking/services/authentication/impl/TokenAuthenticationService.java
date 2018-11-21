package com.banking.services.authentication.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.entity.User;
import com.banking.service.UserService;
import com.banking.services.authentication.TokenService;
import com.banking.services.authentication.UserAuthenticationService;
import com.banking.util.PasswordUtil;
import com.google.common.collect.ImmutableMap;

@Service
public class TokenAuthenticationService implements UserAuthenticationService {

	TokenService tokenService;

	UserService userService;

	@Autowired
	public TokenAuthenticationService(TokenService tokenService, UserService userService) {
		this.tokenService = tokenService;
		this.userService = userService;
	}

	@Override
	public Optional<String> login(final String username, final String password) {

		User user = userService.getByLogin(username);
		if (user != null && PasswordUtil.isMatch(password, user.getPassword())) {
			return Optional.of(tokenService.expiring(ImmutableMap.of("username", username)));
		}
		return Optional.empty();

	}

	@Override
	public Optional<User> findByToken(final String token) {

		Map<String, String> verifiedUser = tokenService.verify(token);
		String username = verifiedUser.get("username");
		return Optional.ofNullable(userService.getByLogin(username));
	}

}