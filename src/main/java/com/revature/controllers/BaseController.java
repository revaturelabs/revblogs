package com.revature.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.revature.app.TemporaryFile;
import com.revature.beans.Blog;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.service.BusinessDelegate;
import com.revature.service.HtmlWriter;
import com.revature.service.JetS3;
import com.revature.service.Logging;
import com.revature.service.Population;
import com.revature.service.impl.JetS3Impl;

@Controller
public class BaseController {
	
	/**
	 * 	Attributes && Getters/Setters
	 * 
	 */
	private Logging logging;
	private BusinessDelegate businessDelegate;
	private Population population;

	public void setBusinessDelegate(BusinessDelegate businessDelegate){
		this.businessDelegate = businessDelegate;
	}
	public BusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}
	public Population getPopulation() {
		return population;
	}
	public void setPopulation(Population population) {
		this.population = population;
	}
	public Logging getLogging() {
		return logging;
	}
	public void setLogging(Logging logging) {
		this.logging = logging;
	}

	/**
	 *  Default Controller
	 *  
	 */
	
	//------------------------------------------------
	// Redirections (&& Population)
	
	// Default Page
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(){
		
		return "index";
	}
	
	// Login Page
	@RequestMapping(value="/loginPage", method=RequestMethod.GET)
	public String login(HttpServletRequest req, HttpServletResponse resp){
		
		return "loginPage";
	}
	
	// Create Blog Page
	@RequestMapping(value="/create-blog", method=RequestMethod.GET)
	public String createBlog(HttpServletRequest req, HttpServletResponse resp){
		
		req.setAttribute("blog", new Blog());
		return "create-blog";
	}
	
	// Upload Example Page
	@RequestMapping(value="/upload-example", method=RequestMethod.GET)
	public String uploadExamplePage() {
		
		return "upload-example";
	}
	
	// Profile Page
	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String profile(HttpServletRequest request, HttpServletRequest response){
		return "profile";
	}
	
	// Add Picture Page
	@RequestMapping(value="/add-picture", method=RequestMethod.GET)
	public String addPicture(HttpServletRequest req, HttpServletResponse resp){
		return "add-picture";
	}
	
	// Database Population (Empty = Database Populated) - No Redirection
	@RequestMapping(value="/populate", method=RequestMethod.GET)
	public void populate(HttpServletRequest req, HttpServletResponse resp){
		
	}
	
	//------------------------------------------------
	// SPRING SECURITY
	
	// Admin Page
	@RequestMapping(value="/admin**")
	public ModelAndView viewAdmin(HttpServletRequest request, HttpServletRequest response, Principal principal){
		String name = principal.getName();
		User user = businessDelegate.requestUsers(name);
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		
		ModelAndView model = new ModelAndView();
		model.setViewName("/home");
		model.addObject("title", "Logged in as " + user.getJobTitle());
		model.addObject("message", "Welcome " + user.getFirstName());
		
		return model;
	}
	
	// Contributor Page
	@RequestMapping(value="/contributor**")
	public ModelAndView viewContributor(HttpServletRequest request, HttpServletRequest response, Principal principal){
		
		String name = principal.getName();
		User user = businessDelegate.requestUsers(name);
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		
		ModelAndView model = new ModelAndView();
		model.setViewName("/home");
		model.addObject("title", "Logged in as " + user.getJobTitle());
		model.addObject("message", "Welcome " + user.getFirstName());
		
		return model;
	}

	//---------------------------------------------------------------------------------------------------------------
	
	@RequestMapping(value="add-blog.do", method=RequestMethod.POST)
	public String addBlog(
			
			@ModelAttribute("blog") @Valid Blog blog, 
			BindingResult bindingResult,
			HttpServletRequest req,
			HttpServletResponse resp) {
		/*
		 * Blog Bean will be generated with proper tags and fields
		 */
		
		if(blog.getBlogTagsString().isEmpty()){
			blog.setTags(new HashSet<Tags>());
		}
		else{
			String tmp = blog.getBlogTagsString();
			List<String> myList = Arrays.asList(tmp.split(","));
			Set<Tags> tmpTags = new HashSet<Tags>();
			List<Tags> dbTags = businessDelegate.requestTags();
			/*
			 * loop through List of tag descriptions the user types in
			 */
			for(String a : myList){
				boolean check = false;
				String tagDesc = a.toLowerCase().replaceAll("\\s+","");
				/*
				 * loop through database Tags to check with user input tags
				 * if theres a match, put instance of database Tag into User bean, if not, create new Tag bean
				 */
				for(Tags b : dbTags){
					if(b.getDescription().equals(tagDesc)){
						tmpTags.add(b);
						check = true;
					}
				}
				if(!check){
					Tags myTag = new Tags(tagDesc);
					businessDelegate.putRecord(myTag);
					tmpTags.add(myTag);
					
				}
			}
			blog.setTags(tmpTags);
		}
		User author = businessDelegate.requestUsers("dpickles");
		blog.setAuthor(author);
		blog.setPublishDate(new Date());
		
		businessDelegate.putRecord(blog);
		req.getSession().setAttribute("blog", blog);
		return "preview-blog";
	}
	
	@RequestMapping(value="publish.do", method=RequestMethod.POST)
	public String publishBlog(HttpServletRequest req, HttpServletResponse resp) {
		Blog blog = (Blog) req.getSession().getAttribute("blog");
		HtmlWriter htmlWriter;
		try {
			InputStream templateStream = this.getClass().getClassLoader().getResourceAsStream("template.html");
			blog.getAuthor();
			htmlWriter = new HtmlWriter(blog, blog.getAuthor(), templateStream);
			TemporaryFile blogTempFile = htmlWriter.render();
			System.out.println(blogTempFile.getTemporaryFile().getName());
			JetS3 jetS3 = new JetS3Impl();
			jetS3.uploadPage(blogTempFile.getTemporaryFile());
			blogTempFile.destroy();
		} catch (FileNotFoundException e) { 
			logging.info(e);
		} catch (IOException e1) {
			logging.info(e1);
		}
		return "success";
	}
}