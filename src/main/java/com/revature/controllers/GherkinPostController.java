package com.revature.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
	
	@ModelAttribute("updateUser")
	public User instantializeNewUser(){
		return new User();
	}
	
	@RequestMapping(value="updateUser.do", method=RequestMethod.POST)
	public String updateUser(@ModelAttribute("updateUser") @Valid User updateUser, BindingResult bindingResult,
			HttpServletRequest req, HttpServletResponse resp){
		
		if(bindingResult.hasErrors()){
			return "profile";
		}
		User loggedIn = (User) req.getAttribute("loggedIn");
		loggedIn.setUsername(updateUser.getUsername());
		loggedIn.setEmail(updateUser.getEmail());
		loggedIn.setFirstName(updateUser.getFirstName());
		loggedIn.setLastName(updateUser.getLastName());
		loggedIn.setJobTitle(updateUser.getJobTitle());
		loggedIn.setLinkedInURL(updateUser.getLinkedInURL());
		loggedIn.setDescription(updateUser.getDescription());
		businessDelegate.putRecord(loggedIn);
		return "index";
	}
	
	@RequestMapping(value="updatePassword.do", method=RequestMethod.POST)
	public String updatePassword(@RequestParam("newPassword") String password,
			HttpServletRequest req, HttpServletResponse resp){
		
		User loggedIn = (User) req.getAttribute("loggedIn");
		loggedIn.setPassword(password);
		businessDelegate.putRecord(loggedIn);
		return "index";
		
	}
	
	@RequestMapping(value="uploadProfilePicture", method=RequestMethod.POST)
	public void uploadProfilePicture(@RequestParam("profilePicture") MultipartFile profilePicture, 
			HttpServletRequest req, HttpServletResponse resp)
	{
		String url = businessDelegate.uploadEvidence(profilePicture.getOriginalFilename(), profilePicture);
		User loggedIn = (User) req.getAttribute("loggedIn");
		loggedIn.setProfilePicture(url);
		businessDelegate.putRecord(loggedIn);
	}
}
