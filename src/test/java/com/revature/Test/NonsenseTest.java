package com.revature.Test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.revature.nonsense.NonsenseGenerator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={NonsenseGenerator.class})
public class NonsenseTest {

	@Autowired
	private NonsenseGenerator nonsenseGenerator;
	
	@Test
	public void TestNonsenseGenerator(){
		assertNotNull(nonsenseGenerator);
	}
}
