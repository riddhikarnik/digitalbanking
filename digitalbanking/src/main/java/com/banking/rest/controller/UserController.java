package com.banking.rest.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.banking.entity.User;
import com.banking.rest.constants.RestURIConstants;
import com.banking.service.UserService;
import com.banking.service.validator.UserValidator;
import com.banking.util.exceptions.InvalidInputException;
import com.banking.util.exceptions.UserAlreadyExistsException;
import com.banking.util.exceptions.UserNotFoundException;

@RestController
@RequestMapping(RestURIConstants.USERS)
public class UserController {

	private static final Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	private UserValidator userValidator;

	@PostMapping("/delete/{userId}")
	public ResponseEntity<Boolean> deleteUser(@PathVariable("userId") final Integer userId) throws UserNotFoundException {
		userService.delete(userId);
		return new ResponseEntity<>(new Boolean(true), HttpStatus.OK);
	}

	@RequestMapping(value = RestURIConstants.UPDATE, method = RequestMethod.POST)
	public @ResponseBody User updateUser(@RequestBody com.banking.rest.request.UserLoginDetails user)
			throws UserAlreadyExistsException {

		logger.info("Start updateUser.");

		return this.userService.save(userService.convert(user));
	}

	@RequestMapping(value = RestURIConstants.CREATE, method = RequestMethod.POST)
	public @ResponseBody User createUser(@RequestBody User user)
			throws InvalidInputException, UserAlreadyExistsException {

		logger.info("Start createUser.");
		logger.info("Input " + user);

		return this.userService.save(user);
	}

	@RequestMapping(value = RestURIConstants.RETRIEVE, method = RequestMethod.GET)
	public @ResponseBody List<User> listUsers() {

		logger.info("Start listUsers.");

		List<User> users = this.userService.getAll();

		return users.stream().sorted(Comparator.comparing(User::getLastName)).collect(Collectors.toList());

	}

	public UserValidator getUserValidator() {
		return userValidator;
	}

	public void setUserValidator(UserValidator userValidator) {
		this.userValidator = userValidator;
	}

}
