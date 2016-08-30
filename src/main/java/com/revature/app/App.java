package com.revature.app;

import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.service.BusinessDelegate;
import com.revature.service.impl.BusinessDelegateImpl;

public class App {
	
	/*
	public static void main(String[] args) {
		
		
		 //Cannot prepopulate the database within MAIN for some reason.... Going to attempt to populate with a 
		 //predefined method connected to the front end for a short while.
		 
		BusinessDelegate bd = new BusinessDelegateImpl();
		
		User user = new User(	1, 
								"dpickles", 
								"pickles1", 
								"pickles@yahoo.com", 
								"Dan", 
								"Pickles", 
								null, 
								"Java Developer", 
								null, 
								"I am a awesome Java developer who focuses on creating applications that focus on pickles.", 
								new UserRoles(1, "ADMIN"), 
								true, 
								null
							);
		
		bd.putRecord(user);
		
		System.out.println("Inserted!");
		
		User myUser = bd.requestUsers("dpickles");
		
		
		System.out.println("My User is: " + myUser.getFirstName());
	}
	*/
}
