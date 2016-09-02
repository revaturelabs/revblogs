package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.service.BusinessDelegate;
import com.revature.service.Logging;

@Controller
@SessionAttributes("roleDropDown")
public class GetController {

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
	
	@ModelAttribute("roleDropDown")
	public List<UserRoles> instantiateRoleDrop(){
		return businessDelegate.requestRoles();
	}
	
	/**
	 *  Methods that pull from the database
	 *  
	 */
	
	@RequestMapping(value="/makeClientAccount", method=RequestMethod.GET)
	public String newClient(HttpServletRequest req, HttpServletResponse resp){
		req.setAttribute("user", new User());
		List<UserRoles> arrl = new ArrayList<>();
		arrl.add(new UserRoles(1, "Manager"));
		arrl.add(new UserRoles(2, "Employee"));
		
		req.setAttribute("roleDropDown", arrl);
		return "makeClientAccount";
	}
	
	
}
