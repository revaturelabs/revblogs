package com.revature.service;

import java.io.File;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.revature.beans.Blog;
import com.revature.beans.Evidence;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;

@Service
public interface BusinessDelegate {
	
	public Session requestSession();
	
	/**
	 * Attempts to upload a resource (such as an image) to the S3 server
	 * @param fileName the destination name of the file, a valid extension should be included
	 * @param file a file that is to be uploaded to the database
	 * @return the URL where the file was uploaded if successful, null otherwise
	 */
	public String uploadResource(String fileName, MultipartFile file);
	
	/**
	 * Attempts to upload a front-end page to the S3 server
	 * @param file a file that is to be uploaded to the database, the file should have a valid extension
	 * @return the URL where the file was uploaded if successful, null otherwise
	 */
	public String uploadPage(File file);
	
	// Push
	public void putRecord(Object _obj);
	
	// Pull
	public User requestUsers(String _username);
	
	public List<User> requestUsers();
	public List<Blog> requestBlogs();
	public List<Tags> requestTags();
	public List<UserRoles> requestRoles();
	public List<Evidence> requestEvidence();
}
