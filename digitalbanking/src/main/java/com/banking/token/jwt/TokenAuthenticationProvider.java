package com.banking.token.jwt;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.banking.rest.security.LoggedUser;
import com.banking.services.authentication.UserAuthenticationService;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public final class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	UserAuthenticationService auth;

	@Override
	protected void additionalAuthenticationChecks(final UserDetails d, final UsernamePasswordAuthenticationToken auth) {
		// Nothing to do
	}

	@Override
	protected User retrieveUser(final String username, final UsernamePasswordAuthenticationToken authentication) {

		final Object token = authentication.getCredentials();
		Optional<com.banking.entity.User> user = auth.findByToken(token.toString());
		if (user.isPresent()) {
			return new LoggedUser(user.get());
		} else {
			throw new UsernameNotFoundException("Cannot find user with authentication token=" + token);
		}

	}
}
