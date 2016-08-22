package com.revature.data.impl;

import org.hibernate.SessionFactory;

import com.revature.data.DAO;

public class DAOImpl implements DAO{

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	
	
	
}
