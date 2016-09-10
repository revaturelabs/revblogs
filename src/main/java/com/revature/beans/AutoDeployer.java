package com.revature.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;

import com.revature.service.JetS3;
import com.revature.service.impl.JetS3Impl;

public class AutoDeployer implements InitializingBean, ServletContextAware {

	ServletContext ctxt;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		/*JetS3 deployer = new JetS3Impl();
		InputStream iStream = ctxt.getResourceAsStream("pages/index.html");
		byte[] buffer = new byte[iStream.available()];
		File iFile = new File("pages/deploy/index.html");
		OutputStream iOut = new FileOutputStream(iFile);
		iOut.write(buffer);
		iOut.close();
		deployer.uploadInitial(iFile);
		iStream = ctxt.getResourceAsStream("pages/error.html");
		buffer = new byte[iStream.available()];
		iFile = new File("pages/deploy/error.html");
		iOut = new FileOutputStream(iFile);
		iOut.write(buffer);
		deployer.uploadInitial(iFile);
		iOut.close();*/
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		//this.ctxt = arg0;
	}

}
