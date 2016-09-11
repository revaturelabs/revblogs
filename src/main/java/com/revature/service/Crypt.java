package com.revature.service;

import com.revature.data.impl.PropertyType;

public interface Crypt {
	
	public String encrypt(String target, String key1, String key2);
	public String decrypt(String target, String key1, String key2);
	public void setProperty(String[] props);
	public String getProperty(PropertyType type);
	public String getRandom(int length);
}
