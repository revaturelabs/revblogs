package com.revature.beans;

import java.io.File;

import org.springframework.beans.factory.InitializingBean;

import com.revature.service.JetS3;
import com.revature.service.impl.JetS3Impl;

public class AutoDeployer implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		JetS3 deployer = new JetS3Impl();
		deployer.uploadPage(new File("src/main/webapp/WEB-INF/pages/index.html"));
		deployer.uploadPage(new File("src/main/webapp/WEB-INF/pages/error.html"));
		deployer.uploadPage(new File("src/main/webapp/WEB-INF/pages/search.html"));
	}

}
