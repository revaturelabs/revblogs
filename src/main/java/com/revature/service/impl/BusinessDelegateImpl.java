package com.revature.service.impl;

import com.revature.data.DataService;
import com.revature.service.BusinessDelegate;
import com.revature.service.ServiceLocator;

public class BusinessDelegateImpl implements BusinessDelegate{

	private DataService dataService;
	private ServiceLocator serviceLocator;
	
	public void setDataService(DataService dataService) {
		this.dataService = dataService;
	}
	public void setServiceLocator(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}
	
	
	
}
