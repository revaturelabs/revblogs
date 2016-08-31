package com.revature.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.revature.beans.Blog;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.service.impl.BusinessDelegateImpl;
import com.revature.service.impl.Crypt;

public class Population {
	
	BusinessDelegate delegate = new BusinessDelegateImpl();
	
	// ALL TAGS
	List<Tags> tags = new ArrayList<Tags>();
	
	//-----------------------------------
	// Complete Population (Besides Evidence)
	public void populateDatabase(){
		
		populateRoles();
		populateTags();
		populateBlogs();
		populateUsers();
	}
	
	//-----------------------------------
	// Roles
	public void populateRoles(){
		
		/**
		 *  List the roles.
		 *  Iterate through the list.
		 *  Insantiate a new Role for each role description.
		 *  Save that role in the database.
		 */
		
		String[] roles = new String[]{"ADMIN", "CONTRIBUTOR"};
		
		UserRoles role = null;
		
		for(int i = 0; i < roles.length; i++){
			
			role = new UserRoles(roles[i]);
			delegate.putRecord(role);
		}
		
		System.out.println("-- Done Populating Role Table --");
	}
	
	//-----------------------------------
	// Tags
	public void populateTags(){
		
		/**
		 *  List the tags.
		 *  Iterate through the tags.
		 *  Insantiate a new Tag for each tag description.
		 *  Save that tag to the GLOBAL list.
		 *  Save that tag in the database.
		 */
		
		String[] tags = new String[]{"Java, SQL, Apian, JSP, Servlet, Hibernate, Spring, REST, SOAP"};
		
		Tags tag = null;
		
		for(int i = 0; i < tags.length; i++){
			
			tag = new Tags(tags[i]);
			
			this.tags.add(tag);
			
			delegate.putRecord(tag);
		}
		
		System.out.println("-- Done Populating Tags Table --");
	}
	
	//-----------------------------------
	// Blogs
	public void populateBlogs(){
		
		/**
		 *  List the blog data.
		 *  Iterate through the blog data.
		 *  Insantiate a new Blog for each corresponding data element.
		 *  Save that blog in the database.
		 */
		
		String[] blogTitle = new String[]{
			
			// 1
			"Making Web Pages Flexible",
			
			// 2
			"The differences between SOAP and REST Web Services",
			
			// 3
			"Ditching SQL with HIbernate!"	
		};
		
		String[] blogSubtitle = new String[]{
				
			// 1
			"JSP, JSTL, and Servlets",
			
			// 2
			"Hint: Loads of fun here.",
			
			// 3
			"Criteria to the Rescue"	
		};
		
		String[] blogContent = new String[]{
			
			// 1
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris dapibus, est non sodales blandit, "
			+ "dolor odio blandit lorem, eget euismod eros dolor sit amet tortor. Aenean malesuada tempor accumsan. "
			+ "In hac habitasse platea dictumst. Duis commodo metus nulla, et pharetra ipsum venenatis a. Mauris "
			+ "cursus fringilla fermentum. Suspendisse vitae ante nulla. Donec auctor quam ex, at tincidunt lacus "
			+ "volutpat ac.",
			
			// 2
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris dapibus, est non sodales blandit, "
			+ "dolor odio blandit lorem, eget euismod eros dolor sit amet tortor. Aenean malesuada tempor accumsan. "
			+ "In hac habitasse platea dictumst. Duis commodo metus nulla, et pharetra ipsum venenatis a. Mauris "
			+ "cursus fringilla fermentum. Suspendisse vitae ante nulla. Donec auctor quam ex, at tincidunt lacus "
			+ "volutpat ac.",
			
			// 3
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris dapibus, est non sodales blandit, "
			+ "dolor odio blandit lorem, eget euismod eros dolor sit amet tortor. Aenean malesuada tempor accumsan. "
			+ "In hac habitasse platea dictumst. Duis commodo metus nulla, et pharetra ipsum venenatis a. Mauris "
			+ "cursus fringilla fermentum. Suspendisse vitae ante nulla. Donec auctor quam ex, at tincidunt lacus "
			+ "volutpat ac."
		};
		
		// Tag Population Nonsense
		int[][] tagIndexes = new int[][]{
			
			/*
			 * First  [] = Blog #
			 * Second [] = Tags of that Blog
			 * 
			 * Tags: Java, SQL, Apian, JSP, Servlet, Hibernate, Spring, REST, SOAP
			 * 	 Java      (1)
			 *   SQL       (2)
			 *   Apian     (3)
			 *   JSP 	   (4)
			 *   Servlet   (5)
			 *   Hibernate (6)
			 *   Spring    (7)
			 *   REST      (8)
			 *   SOAP      (9)
			 */
	
			// 1
			{1, 0, 0, 4, 5, 0, 0, 0, 0},
			
			// 2
			{1, 0, 0, 0, 0, 0, 0, 8, 9},
			
			// 3
			{1, 2, 0, 0, 0, 6, 0, 0, 0}
		};
		
		Blog blog = null;
		
		for(int i = 0; i < blogTitle.length; i++){
			
			Set<Tags> tagsSet = new HashSet<Tags>();
			
			// Attach the set of Product Categories that correspond to THIS particular product.
			for(int j = 0; j < tags.size(); j++){
				
				if(tagIndexes[i][j] != 0){
					
					tagsSet.add(tags.get(tagIndexes[i][j] - 1));
				}
			}
			
			blog = new Blog(blogTitle[i], blogSubtitle[i], blogContent[i], tagsSet);
			delegate.putRecord(blog);
		}

		System.out.println("-- Done Populating Blogs Table --");
	}
	
	//-----------------------------------
	// Users
	public void populateUsers(){
		
		String[] username = new String[]{
				
				"dpickles",
				"polkadots",
				"tmarx"
		};
		String[] password = new String[]{
				
				"password1",
				"password1",
				"password1"
		};
		String[] email = new String[]{
				
				"pickles@yahoo.com",
				"dots@yahoo.com",
				"marx@yahoo.com"
		};
		String[] firstName = new String[]{
				
				"Dan",
				"John",
				"Tally"
		};
		String[] lastName = new String[]{
				
				"Pickles",
				"Doe",
				"Marx"
		};
		String[] jobTitle = new String[]{
				
				"Java Developer",
				"Java Engineer",
				"Hibernate Wizard"
		};
		String[] linkedInURL = new String[]{
				
				"Fake@linkedin.com",
				"Other@linkedin.com",
				"Almost@linkedin.com"
		};
		String[] description = new String[]{
				
				"I am a software developer who focuses on applications that revolve around pickles.",
				"I am a Java developer with a lot of experience in web services.",
				"I excel at Hibernate and hate SQL."
		};
 		boolean[] isAdmin = new boolean[]{true,false,true};
		
		Criteria criteria1 = delegate.requestSession().createCriteria(UserRoles.class).add(Restrictions.eq("role", "ADMIN"));
		Criteria criteria2 = delegate.requestSession().createCriteria(UserRoles.class).add(Restrictions.eq("role", "CONTRIBUTOR"));
		
		UserRoles admin = (UserRoles) criteria1.uniqueResult();
		UserRoles contributor = (UserRoles) criteria2.uniqueResult();
		
		UserRoles myRole = null;
		
		for(int i = 0; i < username.length; i++){
			
			// Find Role
			if(isAdmin[i]){
				
				myRole = admin;
				
			} else {
				
				myRole = contributor;
			}
			
			User user = new User(username[i], password[i], email[i], firstName[i], lastName[i], jobTitle[i], linkedInURL[i], 
								 description[i], myRole);
			
			// Encrypt Password
			user.setPassword(Crypt.encrypt(user.getPassword(), user.getUsername(), user.getEmail()));
			
			delegate.putRecord(user);
		}
		
		System.out.println("-- Done Populating Users Table --");
	}
}
