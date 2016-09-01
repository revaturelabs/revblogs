package com.revature.data.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.revature.beans.Blog;
import com.revature.beans.Evidence;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.data.DAO;

@Repository
public class DAOImpl implements DAO{

	private SessionFactory sessionFactory;
	private Session session;

	public DAOImpl(){
		super();
	}
	
	public DAOImpl(SessionFactory _factory){
		this();
		setSessionFactory(_factory);
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public void setSession(Session session){
		this.session = session;
	}
	public Session getSession(){
		return session;
	}
	
	// Push
	
	/*
	 * insertRecord will make a record if one does not exist
	 * 
	 */
	
	public void insertRecord(Object _obj){
		
		Session session = sessionFactory.getCurrentSession();
		setSession(session);

		session.save(_obj);
	}
	
	public void editRecord(Object _obj){
		
		session.update(_obj);
	}

	// Pull
	public User getUsers(String _username){
		
		Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("username", _username));
		return (User)criteria.uniqueResult();
	}
	
	public List<User> getUsers(){
			
		Criteria criteria = session.createCriteria(User.class);
		return (List<User>)criteria.list();
	}
	public List<Blog> getBlogs(){
		
		Criteria criteria = session.createCriteria(Blog.class);
		return (List<Blog>)criteria.list();
	}
	public List<Tags> getTags(){
		
		Criteria criteria = session.createCriteria(Tags.class);
		return (List<Tags>)criteria.list();
	}
	public List<UserRoles> getRoles(){
		
		Criteria criteria = session.createCriteria(UserRoles.class);
		return (List<UserRoles>)criteria.list();
	}
	public List<Evidence> getEvidence(){

		Criteria criteria = session.createCriteria(Evidence.class);
		return (List<Evidence>)criteria.list();
	}
}
