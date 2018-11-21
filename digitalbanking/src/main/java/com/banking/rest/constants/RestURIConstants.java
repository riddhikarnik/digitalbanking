package com.banking.rest.constants;

public class RestURIConstants {

	public static final String USERS = "/users";
	public static final String CREATE = "/new";
	public static final String UPDATE = "/update ";
	public static final String RETRIEVE = "/get";

	public class ACCOUNT {
		public static final String CREATE = "/accounts/new";
		public static final String UPDATE = "/accounts/update ";
		public static final String RETRIEVE = "/accounts/get/{id}";
		public static final String DEPOSIT = "/accounts/deposit/{id}/{amount}";
		public static final String DEDUCT = "/accounts/deduct";
	}
}
