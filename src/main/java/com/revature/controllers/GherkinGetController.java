package com.revature.controllers;

import org.springframework.stereotype.Controller;

import com.revature.service.BusinessDelegate;

@Controller
public class GherkinGetController {

	private BusinessDelegate businessDelegate;

	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}
	
	
	
	
}
