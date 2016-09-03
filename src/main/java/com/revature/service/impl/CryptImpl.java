package com.revature.service.impl;

public class CryptImpl  {

	/*
	 * Broken Code ATM
	 * 
	 * MUST ADD implements PasswordEncoder
	 * 
	 * 
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
		
		String temp = Crypt.encrypt((String)passwordInput, user.getUsername(), user.getEmail());
		return temp;
	}

	public boolean matches(CharSequence passwordInput, String encodedPassword) {
		
		if(passwordInput.equals(encodedPassword)){
			return true;
		} else {
			return false;
		}
	}
	*/
}
