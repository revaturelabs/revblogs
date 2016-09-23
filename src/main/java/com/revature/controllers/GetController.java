package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.revature.beans.Blog;
import com.revature.dto.UserDTO;
import com.revature.service.BusinessDelegate;
import com.revature.service.Logging;

@Controller
@SessionAttributes("roleDropDown")
public class GetController {

	/*
	 * 	Attributes && Getters/Setters
	 * 
	 */
	private BusinessDelegate businessDelegate;
	private Logging logging;

	public void setBusinessDelegate(BusinessDelegate businessDelegate){
		this.businessDelegate = businessDelegate;
	}
	public BusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}
	public Logging getLogging() {
		return logging;
	}
	public void setLogging(Logging logging) {
		this.logging = logging;
	}
	
	/*
	 *  Methods that pull from the database
	 *  
	 */
	
	@RequestMapping(value="/makeClientAccount", method=RequestMethod.GET)
	public String newClient(HttpServletRequest req, HttpServletResponse resp){
		req.setAttribute("roleDropDown", businessDelegate.requestRoles());
		return "makeClientAccount";
	}
	
	@RequestMapping(value="/manage-S3", method=RequestMethod.GET)
	public String manageS3(HttpServletRequest req, HttpServletResponse resp){
		String[] str = businessDelegate.getList();
		List<String> pages = new ArrayList<>();
		List<String> test = new ArrayList<>();
		List<String> resources = new ArrayList<>();
		List<String> evidence = new ArrayList<>();
		List<String> profiles = new ArrayList<>();
		for(String st:str){
			if(!st.contains("content/")||st.contains("/resources/"))
				resources.add(st);
			else if(st.contains("/evidence/"))
				evidence.add(st);
			else if(st.contains("/pages/"))
				pages.add(st);
			else if(!st.contains("test/")&&st.contains("/profiles/"))
				profiles.add(st);
			else
				test.add(st);
		}
		req.setAttribute("blog", new Blog());
		req.setAttribute("list", pages);
		req.setAttribute("prlist", profiles);
		req.setAttribute("elist", evidence);
		req.setAttribute("tlist", test);
		req.setAttribute("rlist", resources);
		return "management";
	}
	
	@RequestMapping(value="/manageusers", method=RequestMethod.GET)
	public String manageUsers(HttpServletRequest req, HttpServletResponse resp){
		req.setAttribute("userList", businessDelegate.requestUsers());
		req.setAttribute("updateUserProfile", new UserDTO());
		req.setAttribute("roleList", businessDelegate.requestRoles());
		return "manageusers";
	}
}
