package com.revature.data.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.beans.Blog;
import com.revature.beans.Evidence;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.data.DAO;

@Repository
@Transactional
public class DAOImpl implements DAO{

	private SessionFactory sessionFactory;
	private Session session;

	public DAOImpl(){
		super();
	}
	
	public DAOImpl(SessionFactory factory) throws InterruptedException {
		this();
		setSessionFactory(factory);
		
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		fullTextSession.createIndexer().startAndWait();
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
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Blog> getBlogs(String search, int start, int max) {
		Session session = sessionFactory.getCurrentSession();
		setSession(session);
		
		FullTextSession fts = Search.getFullTextSession(session);
		QueryBuilder qb = fts.getSearchFactory().buildQueryBuilder().forEntity(Blog.class).get();
		org.apache.lucene.search.Query searchQuery = 
				qb.keyword()
				  .onFields("title", "subtitle", "author.firstName", "author.lastName", "tags.description")
				  .matching(search)
				  .createQuery();
		
		Query query = fts.createFullTextQuery(searchQuery, Blog.class);
		query.setFirstResult(start);
		query.setMaxResults(max);
		return (List<Blog>)query.list();
				
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PaginatedResultList<Blog> getBlogs(int start, int max) {
		Session session = sessionFactory.getCurrentSession();
		setSession(session);
		
		PaginatedResultList<Blog> blogPosts = new PaginatedResultList<>();
		
		Criteria criteria = session.createCriteria(Blog.class);
		criteria.setProjection(Projections.rowCount());
		blogPosts.setTotalItems((long)criteria.uniqueResult());
		
		criteria = session.createCriteria(Blog.class);
		criteria.addOrder(Order.desc("publishDate"));
		System.err.println(start);
		criteria.setFirstResult(start);
		criteria.setMaxResults(max);
		List<Blog> postList = criteria.list();
		for (Blog post: postList) {
			Hibernate.initialize(post.getTags());
		}
		blogPosts.setItems(postList);
		
		return blogPosts;
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Blog> getBlogs(User author, int start, int max) {
		Session session = sessionFactory.getCurrentSession();
		setSession(session);
		
		Criteria criteria = session.createCriteria(Blog.class);
		criteria.addOrder(Order.desc("publishDate"));
		criteria.add(Restrictions.eq("author", author));
		criteria.setFirstResult(start);
		criteria.setMaxResults(max);
		return (List<Blog>)criteria.list();
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Blog> getBlogs(Tags category, int start, int max) {
		Session session = sessionFactory.getCurrentSession();
		setSession(session);
		
		String hql = "from Tags where tagId eq :tagId left join Blog order by publishDate";
		Query query = session.createQuery(hql).setInteger("tagId", category.getTagId());
		query.setFirstResult(start);
		query.setMaxResults(max);
		return (List<Blog>)query.list();
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
}
