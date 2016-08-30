package com.revature.data;

import java.util.List;

import com.revature.beans.Blog;
import com.revature.beans.Evidence;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;

public interface DataService {
	
	// Push
	public void makeRecord(Object _obj);
	
	// Pull
	public User grabUsers(String _username);
	
	public List<User> grabUsers();
	public List<Blog> grabBlogs();
	public List<Tags> grabTags();
	public List<UserRoles> grabRoles();
	public List<Evidence> grabEvidence();
}
