package com.revature.data;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.revature.beans.Blog;
import com.revature.beans.Evidence;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.data.impl.PaginatedResultList;

@Repository
public interface DAO {
	
	public Session getSession();
	
	// Push
	public void insertRecord(Object obj);
	public void insertRecord(Object[] obj);
	public void editRecord(Object obj);
	public void editRecord(Object[] obj);
	public void deleteRecord(Object obj);
	
	// Pull 1
	public User getUsers(String email);
	public UserRoles getRoles(int roleId);
	public UserRoles getRoles(String role);
	public User getUser(int id);
	public Tags getTag(int id);
	public Blog getBlog(int id);
	
	// Pull All
	public List<User> getUsers();
	public List<Blog> getBlogs();
	public List<Tags> getTags();
	public List<UserRoles> getRoles();
	public List<Evidence> getEvidence();
	
	// Pagination
	public PaginatedResultList<Blog> getBlogs(int start, int max);
	public PaginatedResultList<Blog> getBlogs(String search, int start, int max);
	public PaginatedResultList<Blog> getBlogs(User author, int start, int max);
	public PaginatedResultList<Blog> getBlogs(Tags category, int start, int max);
	
	// Build the Lucene index with blogs currently in database
	public void rebuildIndex() throws InterruptedException;
}
