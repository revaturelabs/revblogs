package com.revature.data;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.revature.beans.Blog;
import com.revature.beans.Evidence;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;

@Repository
public interface DAO {
	
	public Session getSession();
	
	// Push
	public void insertRecord(Object obj);
	
	// Pull
	public User getUsers(String username);
	
	public List<User> getUsers();
	public List<Blog> getBlogs();
	public List<Tags> getTags();
	public List<UserRoles> getRoles();
	public List<Evidence> getEvidence();
}
