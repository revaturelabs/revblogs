package com.revature.Test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.revature.service.impl.BusinessDelegateImpl;
import com.revature.service.impl.CryptImpl;
import com.revature.service.impl.LoginEncoder;
import com.revature.service.impl.JetS3Impl;
import com.revature.service.impl.Mailer;
import com.revature.service.impl.ServiceLocatorImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ BusinessDelegateImpl.class,
		LoginEncoder.class, CryptImpl.class, JetS3Impl.class,
		Mailer.class, ServiceLocatorImpl.class})
public class SerivceImplTest {

	//REQUIRES THE APPLICATION CONTEXT
	//MUST MAKE -context.xml FILE OR FIND A WAY TO 
	//HAVE THE SPRING-BEANS.XML TO ATTACH TO IT!!!!!!!!!!!!!
	
	
	//UPDATE: BUSINESSDELEGATEIMPL, CRYPT, AND MAILER REQUIRE THAT THEY HAVE AN
	//	'APPLICATION CONTEXT' XML IN ORDER TO WORK.
	

	
	@Autowired
	private BusinessDelegateImpl businessDelegateImpl;
	
	@Autowired
	private CryptImpl crypt;
	
	@Autowired
	private LoginEncoder cryptImpl;
	
	@Autowired
	private JetS3Impl jetS3Impl;
	
	@Autowired
	private Mailer mailer;
	
	@Autowired
	private ServiceLocatorImpl serviceLocatorImpl;
	
	@Test
	public void TestServiceImpl(){
		assertNotNull(businessDelegateImpl);
		assertNotNull(crypt);
		assertNotNull(cryptImpl);
		assertNotNull(jetS3Impl);
		assertNotNull(mailer);
		assertNotNull(serviceLocatorImpl);
	}
	
}
