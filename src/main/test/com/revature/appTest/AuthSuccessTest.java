package com.revature.appTest;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.revature.secure.AuthSuccessHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AuthSuccessHandler.class})
public class AuthSuccessTest {

	@Autowired
	private AuthSuccessHandler authSuccessHandler;
	
	@Test
	public void TestAuthSuccessHandler(){
		assertNotNull(authSuccessHandler);
	}
}
