package com.revature.data;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.revature.beans.Blog;
import com.revature.beans.Evidence;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.data.impl.PaginatedResultList;
import com.revature.data.impl.PropertyType;

@Service
public interface DataService {
	
	public Session grabSession();
	
	// Push
	public void makeRecord(Object obj);
	public void changeRecord(Object obj);
	
	// Pull
	public User grabUsers(String username);
	public String grabProperty(PropertyType type);
	
	public List<User> grabUsers();
	public List<Blog> grabBlogs();
	public List<Tags> grabTags();
	public List<UserRoles> grabRoles();
	public List<Evidence> grabEvidence();
	public PaginatedResultList<Blog> grabBlogs(int start, int max);
	public List<Blog> grabBlogs(User author, int start, int max);
	public List<Blog> grabBlogs(Tags category, int start, int max);
	public List<Blog> grabBlogs(String search, int start, int max);
}
