package com.revature.DaoTest;

import static org.junit.Assert.assertNotNull;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.revature.data.DAO;
import com.revature.data.impl.DAOImpl;
/**
 * Not working at this time.
 * @author Eric
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/dao-context.xml"})
public class DAOTest {


	
	@Autowired
	private DAOImpl daoImpl;

	
	@Autowired
	private DAO dao;
	
	@Test
	public void DaoTesting(){
		assertNotNull(daoImpl);
		assertNotNull(dao);
	}
	
}
