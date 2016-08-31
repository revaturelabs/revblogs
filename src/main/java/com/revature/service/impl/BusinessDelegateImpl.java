package com.revature.service.impl;

import java.util.List;

import org.hibernate.Session;

import com.revature.beans.Blog;
import com.revature.beans.Evidence;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;
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
	public Session requestSession(){
		return dataService.grabSession();
	}
	
	// Push
	public void putRecord(Object _obj){
		dataService.makeRecord(_obj);
	}
	
	// Pull
	public User requestUsers(String _username){
		return dataService.grabUsers(_username);
	}
	
	public List<User> requestUsers(){
		return dataService.grabUsers();
	}
	public List<Blog> requestBlogs(){
		return dataService.grabBlogs();
	}
	public List<Tags> requestTags(){
		return dataService.grabTags();
	}
	public List<UserRoles> requestRoles(){
		return dataService.grabRoles();
	}
	public List<Evidence> requestEvidence(){
		return dataService.grabEvidence();
	}
}
