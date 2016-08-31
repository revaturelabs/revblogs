package com.revature.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.revature.beans.User;
import com.revature.service.BusinessDelegate;

@Controller
public class GherkinPostController {

	private BusinessDelegate businessDelegate;

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
		
		businessDelegate.putRecord(updateUser);
		return "index";
	}
}
