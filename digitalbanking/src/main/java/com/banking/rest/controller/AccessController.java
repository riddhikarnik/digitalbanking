package com.banking.rest.controller;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.banking.rest.constants.ErrorMessages;
import com.banking.rest.request.UserLoginDetails;
import com.banking.rest.response.BaseResponse;
import com.banking.service.UserService;
import com.banking.services.authentication.UserAuthenticationService;
import com.banking.util.exceptions.InvalidInputException;
import com.banking.util.exceptions.UserAlreadyExistsException;
import com.banking.util.exceptions.ValidationException;
import com.mysql.jdbc.StringUtils;

@RestController
@RequestMapping("/public")
public class AccessController {

	final static Logger logger = Logger.getLogger(AccessController.class);

	@Autowired
	UserAuthenticationService authentication;

	@Autowired
	UserService userService;

	@GetMapping("/test")
	public String test() {
		logger.info("Testing URL");
		return "Welcome!";
	}

	@PostMapping("/register")
	@ResponseBody
	public BaseResponse register(@RequestBody com.banking.rest.request.UserLoginDetails user)
			throws ValidationException, UserAlreadyExistsException {

		logger.info("Register new user email" + user.getEmail());

		userService.save(userService.convert(user));

		// TODO: Add messaging functionality

		return login(user);
	}

	@RequestMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse login(@RequestBody com.banking.rest.request.UserLoginDetails user) throws ValidationException {

		logger.info("Authenticating user login " + user.getLogin());

		Optional<String> authenticateToken = authentication.login(user.getLogin(), user.getPassword());
		if (authenticateToken.isPresent()) {
			logger.info("Found authenticated token.");
			return new BaseResponse(authenticateToken.get());
		}
		throw new ValidationException(ErrorMessages.INVALID_LOGIN);
	}

	/**
	 * Throwing Unchecked Exception
	 */
	public void validateUser(UserLoginDetails user) throws InvalidInputException {

		if (StringUtils.isEmptyOrWhitespaceOnly(user.getLogin()) || StringUtils.isEmptyOrWhitespaceOnly(user.getEmail())
				|| StringUtils.isEmptyOrWhitespaceOnly(user.getLastName())) {

			throw new InvalidInputException("Required inputs cannot be blank.");
		}
	}
}