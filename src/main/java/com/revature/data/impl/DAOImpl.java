package com.revature.data.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.beans.ApplicationProperties;
import com.revature.beans.Blog;
import com.revature.beans.Evidence;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.data.DAO;
import com.revature.service.impl.Crypt;

@Repository
@Transactional
public class DAOImpl implements DAO{

	private SessionFactory sessionFactory;
	private Session session;

	public DAOImpl(){
		super();
	}
	
	public DAOImpl(SessionFactory factory){
		this();
		setSessionFactory(factory);
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
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void insertRecord(Object obj){
		
		Session session = sessionFactory.getCurrentSession();
		setSession(session);

		session.save(obj);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void editRecord(Object obj){
		
		Session session = sessionFactory.getCurrentSession();
		setSession(session);
		
		session.update(obj);
	}

	// Pull
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public User getUsers(String username){
		
		Session session = sessionFactory.getCurrentSession();
		setSession(session);
		
		Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("username", username));
		return (User)criteria.uniqueResult();
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<User> getUsers(){
			
		Session session = sessionFactory.getCurrentSession();
		setSession(session);
		
		Criteria criteria = session.createCriteria(User.class);
		return (List<User>)criteria.list();
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<Blog> getBlogs(){
		
		Session session = sessionFactory.getCurrentSession();
		setSession(session);
		
		Criteria criteria = session.createCriteria(Blog.class);
		return (List<Blog>)criteria.list();
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<Tags> getTags(){
		
		Session session = sessionFactory.getCurrentSession();
		setSession(session);
		
		Criteria criteria = session.createCriteria(Tags.class);
		return (List<Tags>)criteria.list();
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<UserRoles> getRoles(){
		
		Session session = sessionFactory.getCurrentSession();
		setSession(session);
		
		Criteria criteria = session.createCriteria(UserRoles.class);
		return (List<UserRoles>)criteria.list();
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<Evidence> getEvidence(){

		Session session = sessionFactory.getCurrentSession();
		setSession(session);
		
		Criteria criteria = session.createCriteria(Evidence.class);
		return (List<Evidence>)criteria.list();
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String getProperty(PropertyType type){
		
		Session session = sessionFactory.getCurrentSession();
		setSession(session);
		
		Criteria criteria = session.createCriteria(ApplicationProperties.class);
		ApplicationProperties props = (ApplicationProperties) criteria.uniqueResult();

		switch(type){
		
			case COMPANY:
			
				props.setCompany(Crypt.decrypt(props.getCompany(), props.getApp(), props.getS3()));
				
				System.out.println("Company is = " + props.getCompany());
				
				return props.getCompany();
				
			case APP:
				break;
			case S3:
				break;
			case SERVER:
				break;
			case JENKINS:
				break;
			case SONARQUBE:
				break;
			case K:
				break;
			case V:
				break;
		}
		
		return null;
	}
}
