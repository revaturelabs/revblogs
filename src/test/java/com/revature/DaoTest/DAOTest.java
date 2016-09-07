package com.revature.DaoTest;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
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
/**
 * TRYING TO ESTABLISH A CONNECTION FROM THE DAO TO THE DATABASE. SO FAR, 
 * 		I AM ONLY ABLE TO INCLUDE THE DAO AND BEAN I WISH TO WORK WITH.
 * 		THE ATTACHED FILE TO THIS PROGRAM (/dao-context.xml) HAS THE
 * 		STRUCTURE OF CONNECTIONS.
 * @author Eric
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/dao-context.xml"})
public class DAOTest{
	private Logger log = Logger.getRootLogger();
	@Autowired
    private ApplicationContext applicationContext;
 

	@Autowired
	private DAOImpl daoImpl;
	
	@Autowired
	private DAO dao;
	
	@Autowired
	private User user;
	
	@Autowired
	private UserRoles userRoles;
	
	@Test
	public void daoTesting(){
		assertNotNull(daoImpl);
		assertNotNull(dao);
		assertNotNull(user);
		assertNotNull(userRoles);
	}
	
	
	//Test fails
	@Test
	public void findUser(){
		
		try{
			user = dao.getUsers("pickles1@yahoo.com");
		}catch(Exception e){
			log.error(e);
		}
		
	}

	
}
