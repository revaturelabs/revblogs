package com.revature.service.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.revature.beans.User;
import com.revature.service.BusinessDelegate;

public class CryptImpl implements PasswordEncoder{

	private BusinessDelegate businessDelegate;
	private User user;
	
	public BusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}
	
	public String encode(CharSequence passwordInput) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Session session = businessDelegate.requestSession();
		Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("username", username));
		user = (User)criteria.uniqueResult();
		
		String temp = Crypt.encrypt((String)passwordInput, user.getEmail(), user.getFullname());
		return temp;
	}

	public boolean matches(CharSequence passwordInput, String encodedPassword) {
		
		if(passwordInput.equals(encodedPassword)){
			return true;
		} else {
			return false;
		}
	}
}
