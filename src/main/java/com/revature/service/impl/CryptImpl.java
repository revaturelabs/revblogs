package com.revature.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.controllers.AjaxController;
import com.revature.service.BusinessDelegate;

@Service
public class CryptImpl implements PasswordEncoder{

	/*
	 *  Attributes && Getters/Setters
	 */
	
	public static User user = new User();
	
	private BusinessDelegate businessDelegate;
	private AjaxController ajax;
	
	public BusinessDelegate getBusinessDelegate() {		
		return businessDelegate;
	}
	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}
	public AjaxController getAjax() {
		return ajax;
	}
	public void setAjax(AjaxController ajax) {
		this.ajax = ajax;
	}
	
	/*
	 * Password Encoder implementation for Custom Encryption
	 * @see org.springframework.security.crypto.password.PasswordEncoder#encode(java.lang.CharSequence)
	 */
	
	
	public String encrypt(CharSequence passwordInput){
		
		String temp = Crypt.encrypt((String)passwordInput, user.getEmail(), user.getFullname());
		return temp;
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
		
		//String temp = Crypt.encrypt((String)passwordInput, user.getEmail(), user.getFullname());
		//return temp;
		
		return "";
	}
	public boolean matches(CharSequence passwordInput, String encodedPassword) {

		if(encodedPassword.equals(encrypt(passwordInput))){
			return true;
		} else {
			return false;
		}
	}
}
