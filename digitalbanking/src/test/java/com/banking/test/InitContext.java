package com.banking.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/spring-app.xml", "classpath*:/spring/spring-db.xml",
		"classpath*:/spring/spring-mvc.xml" })
@WebAppConfiguration
@EnableWebMvc
public class InitContext {
	@Autowired
	protected WebApplicationContext ctx;

	protected MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
//		PowerMockito.mockStatic(PasswordUtil.class);
	}

	@Test
	public void testInit() {
		System.out.println("Initialized test context");
		assert (true);
	}
}
