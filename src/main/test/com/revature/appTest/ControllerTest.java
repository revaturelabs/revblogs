package com.revature.appTest;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.revature.controllers.AjaxController;
import com.revature.controllers.BaseController;
import com.revature.controllers.GetController;
import com.revature.controllers.PostController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AjaxController.class, BaseController.class,
		GetController.class, PostController.class})
public class ControllerTest {
	
	@Autowired
	private AjaxController ajaxController;
	
	@Autowired
	private BaseController baseController;
	
	@Autowired
	private GetController getController;
	
	@Autowired
	private PostController postController;
	
	@Test
    public void verifyControllers() {
		assertNotNull(ajaxController);
		assertNotNull(baseController);
		assertNotNull(getController);
		assertNotNull(postController);
		
	}

	
}
