package com.revature.controllers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.revature.beans.ApplicationProperties;
import com.revature.beans.Blog;
import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.data.impl.PropertyType;
import com.revature.service.BusinessDelegate;
import com.revature.service.Logging;
import com.revature.service.Population;

@Controller
public class BaseController {
	
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
	
	
	//CHANGED LOGIN TO FUNCTION CORRECTLY
	
	@RequestMapping(value="/loginPage")
	public String login(HttpServletRequest req, HttpServletResponse resp){
	
		return "loginPage";
	}
	@RequestMapping(value="/temp-AddClient", method=RequestMethod.GET)
	public String newClient(HttpServletRequest req, HttpServletResponse resp){
		req.setAttribute("user", new User());
		List<UserRoles> arrl = new ArrayList<>();
		arrl.add(new UserRoles(1, "Manager"));
		arrl.add(new UserRoles(2, "Employee"));
		
		req.setAttribute("roleDropDown", arrl);
		return "makeClientAccount";
	}
	@RequestMapping(value="/populate", method=RequestMethod.GET)
	public String populate(HttpServletRequest req, HttpServletResponse resp){
	
		return "loginPage";
	}
	@RequestMapping(value="/create-blog", method=RequestMethod.GET)
	public String createBlog(HttpServletRequest req, HttpServletResponse resp){
		req.setAttribute("blog", new Blog());
		return "create-blog";
	}

	@RequestMapping(value="add-blog.do", method=RequestMethod.POST)
	public String addBlog(
			@ModelAttribute("blog") @Valid Blog blog, 
			BindingResult bindingResult,
			HttpServletRequest req,
			HttpServletResponse resp) {
		return "create-blog";
	}

	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	
	@RequestMapping(value="/upload-example", method=RequestMethod.GET)
	public ModelAndView uploadExamplePage() {
		return new ModelAndView("upload-example");
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
	@RequestMapping(value="/add-picture", method=RequestMethod.GET)
	public String addPicture(HttpServletRequest req, HttpServletResponse resp){
		return "add-picture";
	}
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
	
	//SEPARATE THE LOGINS FOR ADMIN AND CONTRIBUTOR
	
	@RequestMapping(value="/admin**")
	public ModelAndView viewAdmin(){
		
		ModelAndView model = new ModelAndView();
		model.setViewName("/home");
		model.addObject("title", "That one guy");
		model.addObject("message", "Welcome");
		
		return model;
	}
	
	
	@RequestMapping(value="/contributor**")
	public ModelAndView viewContributor(){
		
		ModelAndView model = new ModelAndView();
		model.setViewName("/home");
		model.addObject("title", "That one guy");
		model.addObject("message", "Welcome");
		
		return model;
	}
	
}
