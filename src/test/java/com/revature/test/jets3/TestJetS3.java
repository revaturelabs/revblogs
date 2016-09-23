package com.revature.test.jets3;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.revature.service.JetS3;
import com.revature.service.impl.JetS3Impl;

/**
 * EXAMPLE OF A CLASS REQUESTING AN 'APPLICATION CONTEXT' XML.
 * 		IF NEEDED, CREATE A SPRING BEAN IN THE SRC/TEST/RESOURCES FOLDER AND
 * 		LINK IT TO THE TEST, VIA THE @CONTEXTCONFIGURATION(LOCATIONS = "/ (YOUR XML HERE).XML") 
 *  	AS SHOWN BELOW.
 *  
 *  STILL NEED TO FIND SOMETHING TO MOCK THE WEB PAGES AND ALLOW
 *  	US TO TEST AGAINST THEM. 
 * @author Eric
 *
 */

/*
 * YOU CAN ONLY DO EITHER LOCATIONS OR CLASSES, BOTH CANNOT OCCUR AT THE SAME TIME FOR THE 
 * 		VERSION OF SPRING AND JUNIT WE ARE USING. HOWEVER, HOW I CURRENTLY HAVE IT SET UP, 
 * 		IT PASSES THE TESTS.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/context1.xml")
public class TestJetS3 {
	
	@Autowired
	private JetS3Impl jetS3Impl;
	
	@Autowired
	private JetS3 jetS3;
	
	@Test
	public void TestingJetS3(){
		assertNotNull(jetS3Impl);
		assertNotNull(jetS3);
	}
}
