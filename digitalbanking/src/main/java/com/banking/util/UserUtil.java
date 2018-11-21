package com.banking.util;

import com.banking.entity.User;

public class UserUtil {

	public static User decorate(User user) {

		user.setPassword(PasswordUtil.encode(user.getPassword()));
		user.setLoginId(user.getLoginId().toLowerCase());
		user.setEmail(user.getEmail().toLowerCase());
		return user;
	}
}
