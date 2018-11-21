package com.banking.service;

import java.util.List;

import com.banking.entity.User;
import com.banking.util.exceptions.UserAlreadyExistsException;
import com.banking.util.exceptions.UserNotFoundException;

public interface UserService {

	User get(int userId);

	User save(User user) throws UserAlreadyExistsException;

	User update(User user);

	List<User> getAll();

	User getByLogin(String email);

	User convert(com.banking.rest.request.UserLoginDetails user);

	User convertForUpdate(com.banking.rest.request.UserLoginDetails user);

	void delete(int userId) throws UserNotFoundException;
	

}
