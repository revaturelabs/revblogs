package com.revature.data;

import java.util.List;

import org.hibernate.Session;

import com.revature.beans.Blog;
import com.revature.beans.Evidence;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;

public interface DAO {
	
	public Session getSession();
	
	// Push
	public void insertRecord(Object _obj);
	
	// Pull
	public User getUsers(String _username);
	
	public List<User> getUsers();
	public List<Blog> getBlogs();
	public List<Tags> getTags();
	public List<UserRoles> getRoles();
	public List<Evidence> getEvidence();
}
