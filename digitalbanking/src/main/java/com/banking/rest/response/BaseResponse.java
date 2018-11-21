package com.banking.rest.response;

import java.util.List;

public class BaseResponse {

	String token;

	List<String> errorMessages;

	public String getToken() {
		return token;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public BaseResponse(String token) {
		this.token = token;
	}

	public BaseResponse(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

}
