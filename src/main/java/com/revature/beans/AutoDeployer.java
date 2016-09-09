package com.revature.beans;

import java.io.File;

import org.springframework.beans.factory.InitializingBean;

import com.revature.service.JetS3;
import com.revature.service.impl.JetS3Impl;

public class AutoDeployer implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		JetS3 deployer = new JetS3Impl();
		deployer.uploadInitial(new File("resources/index.html"));
		deployer.uploadInitial(new File("resources/error.html"));
		deployer.uploadInitial(new File("resources/search.html"));
	}

}
