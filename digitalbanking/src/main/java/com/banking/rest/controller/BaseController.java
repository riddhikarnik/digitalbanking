package com.banking.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.banking.rest.security.LoggedUser;
import com.banking.service.UserService;

public class BaseController {

	@Autowired
	UserService userService;

	public int getLoggedInUserId() {

		LoggedUser loggedInUser = (LoggedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.getByLogin(loggedInUser.getUsername()).getId();
	}
}
