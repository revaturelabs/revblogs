package com.revature.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.revature.beans.User;
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

	
	/*
	 * 
	 * 	Encode comes from: the Spring Password Encoder, but doesn't allow me to use Getters/Setters for some stupid reason.
	 * 					   It requires an implementation, but it's not required to be called anywhere. DO NOT REMOVE! I was 
	 * 					   able to bypass it by implementing the same exact lines in a custom method and calling that instead.
	 * 
	 * 					   Oh Spring! Why do you hurt me so?
	 */
	
	public String encode(CharSequence passwordInput) {

		return null;
	}
	public boolean matches(CharSequence passwordInput, String encodedPassword) {
		
		return businessDelegate.validate((String)passwordInput, encodedPassword);
	}
}
