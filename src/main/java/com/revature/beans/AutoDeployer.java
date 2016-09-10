package com.revature.beans;

import org.springframework.beans.factory.InitializingBean;

public class AutoDeployer implements InitializingBean {
	//BusinessDelegate businessDelegate = new BusinessDelegateImpl();
	
	
	public void afterPropertiesSet() throws Exception {
		//businessDelegate.uploadInitial(new File("resources/index.html"));
		//businessDelegate.uploadInitial(new File("resources/error.html"));
		//businessDelegate.uploadInitial(new File("resources/search.html"));
	}

}
