package com.revature.data.impl;

import com.revature.data.DAO;
import com.revature.data.DataService;

public class DataServiceImpl implements DataService{

	private DAO dao;

	public void setDao(DAO dao) {
		this.dao = dao;
	}
	
}
