package com.revature.service;

import com.revature.data.impl.PropertyType;

public interface Crypt {
	
	/**
	 * 
	 *  This method salts && encrypts the target.
	 *  
	 * @param target : The string you want encrypted.
	 * @return Returns the encrypted target.
	 * 
	 */
	public String encrypt(String target);
	
	/**
	 * 
	 * This method is used to compare user supplied values to encrypted fields.
	 * 
	 * @param input  : User supplied value. Such as: password from user.
	 * @param hashed : Encrypted version of that value. Such as: password from database.
	 * @return true if they match, false if they don't.
	 */
	public boolean validate(String input, String hashed);
	
	public void setProperty(String[] props);
	public String getProperty(PropertyType type);
	public String getRandom(int length);
}
