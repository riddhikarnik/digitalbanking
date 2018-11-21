package com.banking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.entity.Account;
import com.banking.entity.Role;
import com.banking.entity.User;
import com.banking.entity.repository.AccountRepository;
import com.banking.entity.repository.UserRepository;
import com.banking.rest.security.LoggedUser;
import com.banking.util.UserUtil;
import com.banking.util.exceptions.UserAlreadyExistsException;
import com.banking.util.exceptions.UserNotFoundException;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public User get(int userId) {
		return userRepository.findOne(userId);
	}

	@Override
	@Transactional
	@CacheEvict(value = "users", allEntries = true)
	public User save(User user) throws UserAlreadyExistsException {

		checkIfExists(user);

		validateInput(user);

		if (user.getRole() == null) {
			user.setRole(Role.USER);
		}

		UserUtil.decorate(user);

		return userRepository.saveAndFlush(user);
	}

	private void checkIfExists(User user) throws UserAlreadyExistsException {

		if (userRepository.findOne(user.getLoginId()) != null)
			throw new UserAlreadyExistsException();

	}

	private void validateInput(User user) {

	}

	@Override
	@Transactional
	@CacheEvict(value = "users", allEntries = true)
	public User update(User user) {
		return userRepository.saveAndFlush(user);
	}

	@Override
	@Cacheable("users")
	public List<User> getAll() {
		return userRepository.findAll();
	}

	@Override
	public User getByLogin(String login) {
		return userRepository.findOne(login);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findOne(username.toLowerCase());
		if (user == null) {
			throw new UsernameNotFoundException("UserLoginDetails " + username + " is not found");
		}
		return new LoggedUser(user);
	}

	@Override
	public User convert(com.banking.rest.request.UserLoginDetails user) {

		User userEntity = new User();
		userEntity.setEmail(user.getEmail());
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		userEntity.setLoginId(user.getLogin());
		userEntity.setPassword(user.getPassword());

		return userEntity;
	}

	@Override
	public User convertForUpdate(com.banking.rest.request.UserLoginDetails user) {

		User userEntity = new User();
		userEntity.setEmail(user.getEmail());
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		return userEntity;
	}

	@Override
	public void delete(int userId) throws UserNotFoundException {
		// TODO Add validations.
		// TODO Use a single call to delete with cascade
		List<Account> accounts = accountRepository.findAccounts(userId);
		for (Account account : accounts) {
			accountRepository.delete(account);
		}
		userRepository.delete(userRepository.findOne(userId));

	}
}
