package com.revature.appTest;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.revature.data.impl.DAOImpl;
import com.revature.data.impl.DataServiceImpl;
import com.revature.data.impl.PaginatedResultList;
import com.revature.data.impl.PropertyType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={DAOImpl.class, DataServiceImpl.class,
		PaginatedResultList.class, PropertyType.class})
public class DataImplTest {
	
	//REQUIRES THE APPLICATION CONTEXT
	//MUST MAKE -context.xml FILE OR FIND A WAY TO 
	//HAVE THE SPRING-BEANS.XML TO ATTACH TO IT!!!!!!!!!!!!!
	
	//UPDATE: DAOIMPL, DATASERVICEIMPL AND PROPERTYTYPE MUST BE IN CONTEXT.XML
	//	IF YOU FIND ANOTHER SOLUTION, PLEASE FEEL FREE TO CHANGE IT AS YOU SEE FIT.
	
	
	@Autowired
	private DAOImpl DaoImpl;
	
	@Autowired
	private DataServiceImpl dataServiceImpl;
	
	@SuppressWarnings("rawtypes") 
	@Autowired
	private PaginatedResultList paginatedResultList;
	
	@Autowired
	private PropertyType propertyType;
	
	@Test
	public void ImplTest(){
		assertNotNull(DaoImpl);
		assertNotNull(dataServiceImpl);
		assertNotNull(paginatedResultList);
		assertNotNull(propertyType);
		
	}
}
