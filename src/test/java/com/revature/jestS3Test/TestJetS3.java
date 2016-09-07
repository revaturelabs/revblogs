package com.revature.jestS3Test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.revature.service.impl.JetS3Impl;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/context1.xml")
public class TestJetS3 {
	
	@Autowired
	private JetS3Impl jetS3;
	
	@Test
	public void TestJetS3(){
		assertNotNull(jetS3);
	}
	
}
