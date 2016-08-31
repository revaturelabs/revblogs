package com.revature.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.web.multipart.MultipartFile;

import com.revature.beans.Blog;
import com.revature.beans.Evidence;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.data.DataService;
import com.revature.service.BusinessDelegate;
import com.revature.service.JetS3;
import com.revature.service.ServiceLocator;

public class BusinessDelegateImpl implements BusinessDelegate{

	private DataService dataService;
	private ServiceLocator serviceLocator;
	private JetS3 jetS3 = new JetS3Impl();
	
	public void setDataService(DataService dataService) {
		this.dataService = dataService;
	}
	public void setServiceLocator(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}
	public Session requestSession(){
		return dataService.grabSession();
	}
	
	/**
	 * Attempts to upload a resource (such as an image) to the S3 server
	 * @param fileName the destination name of the file, a valid extension should be included
	 * @param file a file that is to be uploaded to the database
	 * @return the URL where the file was uploaded if successful, null otherwise
	 */
	public String uploadResource(String fileName, MultipartFile file) {
		return jetS3.uploadResource(fileName, file);
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
