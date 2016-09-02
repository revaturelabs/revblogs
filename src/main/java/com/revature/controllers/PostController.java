package com.revature.controllers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.revature.app.TemporaryFile;
import com.revature.beans.User;
import com.revature.service.BusinessDelegate;
import com.revature.service.Logging;

@Controller
public class PostController {

	/**
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
	
	
	/**
	 *  Model Attributes
	 *  
	 */
	
	@ModelAttribute("updateUser")
	public User instantializeNewUser(){
		return new User();
	}
	
	/**
	 *  Methods that effect the database
	 *  
	 */
	
	
	// Update a User
	@RequestMapping(value="updateUser.do", method=RequestMethod.POST)
	public String updateUser(@ModelAttribute("updateUser") @Valid User updateUser, BindingResult bindingResult,
			HttpServletRequest req, HttpServletResponse resp){
		
		if(bindingResult.hasErrors()){
			return "profile";
		}
		User loggedIn = (User) req.getAttribute("loggedIn");
		loggedIn.setEmail(updateUser.getEmail());
		loggedIn.setFirstName(updateUser.getFirstName());
		loggedIn.setLastName(updateUser.getLastName());
		loggedIn.setJobTitle(updateUser.getJobTitle());
		loggedIn.setLinkedInURL(updateUser.getLinkedInURL());
		loggedIn.setDescription(updateUser.getDescription());
		businessDelegate.putRecord(loggedIn);
		return "index";
	}
	
	// Update a Users Password
	@RequestMapping(value="updatePassword.do", method=RequestMethod.POST)
	public String updatePassword(@RequestParam("newPassword") String password,
			HttpServletRequest req, HttpServletResponse resp){
		
		User loggedIn = (User) req.getAttribute("loggedIn");
		loggedIn.setPassword(password);
		businessDelegate.putRecord(loggedIn);
		return "index";
		
	}
	
	// Updates a Users Profile Picture
	@RequestMapping(value="uploadProfilePicture", method=RequestMethod.POST)
	public void uploadProfilePicture(@RequestParam("profilePicture") MultipartFile profilePicture, 
			HttpServletRequest req, HttpServletResponse resp)
	{
		String url = businessDelegate.uploadEvidence(profilePicture.getOriginalFilename(), profilePicture);
		User loggedIn = (User) req.getAttribute("loggedIn");
		loggedIn.setProfilePicture(url);
		businessDelegate.putRecord(loggedIn);
	}
	
	// Uploads a Blog Picture
	@RequestMapping(value="/upload-picture", method=RequestMethod.POST)
	public void uploadPictureHandler(@RequestParam("file") MultipartFile file, HttpServletResponse resp)
	{
		String url = businessDelegate.uploadEvidence(file.getOriginalFilename(), file);
		try {
			PrintWriter writer = resp.getWriter();
			writer.append(url);
		} catch (IOException e) {
			logging.info(e);
		}
	}
	
	@RequestMapping(value="/upload-resource", method=RequestMethod.POST)
	public void uploadResourceHandler(@RequestParam("file") MultipartFile file, HttpServletResponse resp)
	{
		String url = businessDelegate.uploadResource(file.getOriginalFilename(), file);
		try {
			PrintWriter writer = resp.getWriter();
			writer.append("<html><body><img src=\"" + url + "\" /></body></html>");
		} catch (IOException e) {
			logging.info(e);
		}
	}
	
	@RequestMapping(value="/upload-page", method=RequestMethod.POST)
	public void uploadPageHandler(@RequestParam("file") MultipartFile file, HttpServletResponse resp)
	{
		TemporaryFile tempFile = TemporaryFile.make(file);
		File convertedFile = tempFile.getTemporaryFile();
		String url = businessDelegate.uploadPage(convertedFile);
		try {
			PrintWriter writer = resp.getWriter();
			writer.append("<html><body><a href=\"" + url + "\">" + url + "</a></body></html>");
		} catch (IOException e) {
			logging.info(e);
		}
	}
}
