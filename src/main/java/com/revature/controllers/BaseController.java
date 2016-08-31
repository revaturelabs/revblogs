package com.revature.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.revature.service.BusinessDelegate;

@Controller
public class BaseController {

	private BusinessDelegate businessDelegate;

	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(HttpServletRequest req, HttpServletResponse resp){
	
		return "login";
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	
}
