package com.revature.Test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.revature.service.BusinessDelegate;
import com.revature.service.HtmlWriter;
import com.revature.service.JetS3;
import com.revature.service.Logging;
import com.revature.service.Population;
import com.revature.service.ServiceLocator;
import com.revature.service.WriteTester;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ BusinessDelegate.class, HtmlWriter.class, JetS3.class,
		Logging.class, Population.class, ServiceLocator.class,WriteTester.class})
public class ServiceTest {
	
	//REQUIRES THE APPLICATION CONTEXT
	//MUST MAKE -context.xml FILE OR FIND A WAY TO 
	//HAVE THE SPRING-BEANS.XML TO ATTACH TO IT!!!!!!!!!!!!!

	
	//UPDATE: BUSINESSDELEGATE, HTMLWRITER, JETS3, SERVICELOCATOR, AND WRITETESTER NEED AN
	//	'APPLICATION CONTEXT' XML FILE TO WORK PROPERLY. 
	
	@Autowired
	private BusinessDelegate businessDelegate;
	
	@Autowired
	private HtmlWriter htmlWriter;
	
	@Autowired
	private JetS3 jetS3;
	
	@Autowired
	private Logging logging;
	
	@Autowired
	private Population population;
	
	@Autowired
	private ServiceLocator serviceLocator;
	
	@Autowired
	private WriteTester writeTester;
	
	@Test
	public void TestServices(){
		assertNotNull(businessDelegate);
		assertNotNull(htmlWriter);
		assertNotNull(jetS3);
		assertNotNull(logging);
		assertNotNull(population);
		assertNotNull(serviceLocator);
		assertNotNull(writeTester);
	}

}
