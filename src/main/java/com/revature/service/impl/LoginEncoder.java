package com.revature.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.revature.service.BusinessDelegate;

@Service
public class LoginEncoder implements PasswordEncoder{

	/*
	 *  Attributes && Getters/Setters
	 */
	
	private BusinessDelegate businessDelegate;
	
	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}
	
	// This empty method requires an implementation of Password Encoder, 
	// but is not needed.
	public String encode(CharSequence passwordInput) {
		
		return null;
	}
	public boolean matches(CharSequence passwordInput, String encodedPassword) {
		
		return businessDelegate.validate((String)passwordInput, encodedPassword);
	}
}
