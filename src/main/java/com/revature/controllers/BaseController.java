package com.revature.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.revature.service.BusinessDelegate;
import com.revature.service.Population;

@Controller
public class BaseController {

	private BusinessDelegate businessDelegate;
	private Population population;

	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
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
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(HttpServletRequest req, HttpServletResponse resp){
	
		return "login";
	}
	@RequestMapping(value="/populate", method=RequestMethod.GET)
	public String populate(HttpServletRequest req, HttpServletResponse resp){
	
		population.populateDatabase();
		
		return "login";
	}
	@RequestMapping(value="/create-blog", method=RequestMethod.GET)
	public String createBlog(HttpServletRequest req, HttpServletResponse resp){
	
		return "create-blog";
	}
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	
}
