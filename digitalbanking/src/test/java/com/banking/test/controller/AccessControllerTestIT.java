package com.banking.test.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.banking.entity.User;
import com.banking.service.UserService;
import com.banking.services.authentication.UserAuthenticationService;
import com.banking.test.InitContext;

@RunWith(SpringJUnit4ClassRunner.class)
public class AccessControllerTestIT extends InitContext {

	@MockBean
	private UserService userService;

	@MockBean
	UserAuthenticationService authentication;

	@Test
	public void test() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/public/test")).andExpect(status().isOk());

	}

	@Test
	public void testRegister() throws Exception {

		when(userService.convert(Mockito.any(com.banking.rest.request.UserLoginDetails.class))).thenReturn(getMockUser());
		when(userService.save(getMockUser())).thenReturn(getMockUser());
		when(authentication.login(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of("dummyToken"));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/public/register")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(getRequestJSON())
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(jsonPath("$.token").exists());

	}

	@Test
	public void testLogin() throws Exception {

		when(authentication.login(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of("dummyToken"));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/public/login")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(getRequestJSON())
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(jsonPath("$.token").exists());

	}

	@Test
	public void testLoginFailure() throws Exception {

		when(authentication.login(Mockito.anyString(), Mockito.anyString())).thenReturn(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/public/register")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(getRequestJSON())
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(status().is(400));

	}

	private User getMockUser() {
		// TODO Auto-generated method stub
		return new User(1, TESTLOGIN, TESTPASSWORD, "DummyFirstName", "DummyLastName", TESTEMAIL);
	}

	String TESTLOGIN = "riddhikarnikTEST";
	String TESTEMAIL = "testuser@home.com";
	String TESTPASSWORD = "password";

	String getRequestJSON() {
		return "{\"login\": \"" + TESTLOGIN + "\",\n" + "   \"email\" : \"" + TESTEMAIL + "\"," + "   \"password\": \""
				+ TESTPASSWORD + "\"," + "   \"firstName\": \"DummyFirstName\"," + "   \"lastName\": \"DummyLastName\""
				+ " }";
	}

}