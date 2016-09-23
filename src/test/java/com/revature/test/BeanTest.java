package com.revature.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.revature.beans.ApplicationProperties;
import com.revature.beans.Blog;
import com.revature.beans.Evidence;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={User.class, UserRoles.class, Tags.class, 
		Evidence.class, Blog.class, ApplicationProperties.class})
public class BeanTest {

	@Autowired
	private User user;
	
	@Autowired
	private UserRoles userRoles;
	
	@Autowired
	private Tags tags;
	
	@Autowired
	private Evidence evidence;
	
	@Autowired
	private Blog blog;
	
	@Autowired
	private ApplicationProperties applicationProperties;
	
	@Test
    public void verifyBeans() {
		assertNotNull(user);
		assertNotNull(userRoles);
		assertNotNull(tags);
		assertNotNull(evidence);
		assertNotNull(blog);
		assertNotNull(applicationProperties);
	}
}
