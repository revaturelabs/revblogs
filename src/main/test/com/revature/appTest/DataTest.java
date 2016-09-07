package com.revature.appTest;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.revature.data.DAO;
import com.revature.data.DataService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={DAO.class, DataService.class})
public class DataTest {

	//REQUIRES THE APPLICATION CONTEXT
	//MUST MAKE -context.xml FILE OR FIND A WAY TO 
	//HAVE THE SPRING-BEANS.XML TO ATTACH TO IT!!!!!!!!!!!!!
	
	//UPDATE: BOTH REQUIRE THE 'APPLICATION CONTEXT' FILE.
	@Autowired
	private DAO dao;
	
	@Autowired
	private DataService dataService;
	
	@Test
	public void testData(){
		assertNotNull(dao);
		assertNotNull(dataService);
	}
	
}
