	package com.revature.test.dao;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.data.DAO;
import com.revature.data.impl.DAOImpl;
import com.revature.data.impl.DataServiceImpl;
/**
 * TRYING TO ESTABLISH A CONNECTION FROM THE DAO TO THE DATABASE. SO FAR, 
 * 		I AM ONLY ABLE TO INCLUDE THE DAO AND BEAN I WISH TO WORK WITH.
 * 		THE ATTACHED FILE TO THIS PROGRAM (/dao-context.xml) HAS THE
 * 		STRUCTURE OF CONNECTIONS.
 * @author Eric
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:dao-context.xml"})
public class DAOTest{
	
	private Logger log = Logger.getRootLogger();
	
	@Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private DataServiceImpl dataService;

	@Autowired
	private DAOImpl dao;

	@Autowired
	private User user;
	
	@Autowired
	private UserRoles userRoles;
	
	@Before
	public void stupid(){
		
	}
	
	@Test
	public void daoTesting(){
		assertNotNull(sessionFactory);
		assertNotNull(dao);
		assertNotNull(user);
		assertNotNull(userRoles);
	}
	
	@Test
	public void createUser(){
		
		userRoles = new UserRoles("ADMIN");
		user = new User("test@admin.com", "123456", "Test", "Admin", "The Boss", userRoles);
		
		System.out.println(user.getEmail() + " " + userRoles.getRole());
		
	}	
}
