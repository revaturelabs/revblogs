package com.revature.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.revature.beans.UserRoles;
import com.revature.service.BusinessDelegate;

@Controller
@SessionAttributes("roleDropDown")
public class GherkinGetController {

	private BusinessDelegate businessDelegate;

	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}
	
	@ModelAttribute("roleDropDown")
	public List<UserRoles> instantiateRoleDrop(){
		return businessDelegate.requestRoles();
	}
	
	
}
