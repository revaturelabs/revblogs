package com.revature.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.revature.beans.User;
import com.revature.service.BusinessDelegate;
import com.revature.service.Logging;

@Controller
public class GherkinPostController {

	private BusinessDelegate businessDelegate;
	private Logging logging;

	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}
	
	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String instantializeNewUser(HttpServletRequest req){
		if(req.getSession().getAttribute("updateUser") == null){
			req.setAttribute("updateUser", new User());
			req.getSession().getAttribute("user");
			System.out.println("new user created");
		}
		return "profile";
	}
	
	@RequestMapping(value="updateUser.do", method=RequestMethod.POST)
	public String updateUser(@ModelAttribute("updateUser") @Valid User updateUser, BindingResult bindingResult,
			HttpServletRequest req, HttpServletResponse resp){
		System.out.println("inside update user");
		if(bindingResult.hasErrors()){
			return "profile";
		}
		User loggedIn = (User) req.getSession().getAttribute("user");
		loggedIn.setEmail(updateUser.getEmail());
		loggedIn.setFirstName(updateUser.getFirstName());
		loggedIn.setLastName(updateUser.getLastName());
		loggedIn.setJobTitle(updateUser.getJobTitle());
		loggedIn.setLinkedInURL(updateUser.getLinkedInURL());
		loggedIn.setDescription(updateUser.getDescription());
		
		System.out.println();
		System.out.println(loggedIn.getUserId());
		System.out.println(loggedIn.getEmail());
		System.out.println(loggedIn.getFirstName());
		System.out.println(loggedIn.getLastName());
		System.out.println(loggedIn.getJobTitle());
		System.out.println(loggedIn.getLinkedInURL());
		System.out.println(loggedIn.getDescription());
		System.out.println();
		
		businessDelegate.updateRecord(loggedIn);
		
		return "profile";
	}
	
	@RequestMapping(value="updatePassword.do", method=RequestMethod.POST)
	public String updatePassword(@RequestParam("newPassword") String password,
			HttpServletRequest req, HttpServletResponse resp){
		
		User loggedIn = (User) req.getSession().getAttribute("user");
		loggedIn.setPassword(password);
		businessDelegate.updateRecord(loggedIn);
		return "index";
		
	}
	
	@RequestMapping(value="uploadProfilePicture", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE,
			produces=MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String uploadProfilePicture(@RequestParam("profilePicture") MultipartFile profilePicture, 
			HttpServletRequest req, HttpServletResponse resp)
	{
		String url = businessDelegate.uploadEvidence(profilePicture.getOriginalFilename(), profilePicture);
		System.out.println(url);
		User loggedIn = (User) req.getSession().getAttribute("user");
		loggedIn.setProfilePicture(url);
		System.out.println(loggedIn.getProfilePicture());
		businessDelegate.updateRecord(loggedIn);
		return loggedIn.getProfilePicture();
	}
}
