package com.revature.data.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextQuery;
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
import com.revature.service.Logging;

@Repository
@Transactional
public class DAOImpl implements DAO {

	/*
	 * 	Attributes && Getters/Setters
	 * 
	 */
	
	private SessionFactory sessionFactory;
	private Session session;
	private boolean indexBuilt = false;
	private static final String ACTIVE = "active";
	private static final String PUBLISH = "publishDate";
	private static final int MAX_POSTS_PER_PAGE = 25;
	private static final int PAGES_TO_LOAD = 3;

	public DAOImpl(){
		super();
	}
	
	public DAOImpl(SessionFactory factory) throws InterruptedException {
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
		
		Session ses = sessionFactory.openSession();
		setSession(ses);
		
		return session;
	}
	
	/*
	 *  Database Altering Methods
	 */
	
	// Add a Record
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void insertRecord(Object obj){
		
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		ses.save(obj);
	}
	
	// Batch Add
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void insertRecord(Object[] obj){
		
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);

		for(Object o : obj){
			
			ses.save(o);
		}
	}
	
	// Update a Record
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void editRecord(Object obj){
		
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		ses.update(obj);
	}
	
	// Batch Update
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void editRecord(Object[] obj){
		
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);

		for(Object o : obj){
			
			ses.update(o);
		}
	}
	
	// Delete or Archive
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deleteRecord(Object obj){
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		if(obj instanceof Blog){
			
			// Archive
			((Blog)obj).setActive(false);
			editRecord(obj);
		}
		else if(obj instanceof User){
			
			// Archive
			((User)obj).setActive(false);
			editRecord(obj);
		}
		else {
			
			// Obliterate
			ses.delete(obj);
		}
	}

	/*
	 *  Database Query Methods
	 */
	
	// User by Email
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public User getUsers(String email){
		
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		Criteria criteria = ses.createCriteria(User.class).add(Restrictions.eq("email", email));
		return (User)criteria.uniqueResult();
	}
	
	// User by Id
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public User getUser(int id){
		
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		return (User) ses.get(User.class, id);
	}
	
	// Tag by Id
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Tags getTag(int id){
		
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		return (Tags) ses.get(Tags.class, id);
	}
	
	//Pull a Single Role
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public UserRoles getRoles(String role) {
		
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		Criteria criteria = ses.createCriteria(UserRoles.class);
		criteria.add(Restrictions.eq("role", role));
		return (UserRoles) criteria.uniqueResult();
	}
	
	// All Users
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<User> getUsers(){
			
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		Criteria criteria = ses.createCriteria(User.class);
		return (List<User>)criteria.list();
	}
	
	//Pull a single Role
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public UserRoles getRoles(int roleId) {
		
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		Criteria criteria = ses.createCriteria(UserRoles.class);
		criteria.add(Restrictions.eq("userRoleId", roleId));
		return (UserRoles) criteria.uniqueResult();
	}
	
	// All Blogs
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<Blog> getBlogs(){
		
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		Criteria criteria = ses.createCriteria(Blog.class).setMaxResults(MAX_POSTS_PER_PAGE*PAGES_TO_LOAD);
		return (List<Blog>)criteria.list();
	}
	
	// All Tags
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<Tags> getTags(){
		
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		Criteria criteria = ses.createCriteria(Tags.class);
		return (List<Tags>)criteria.list();
	}
	
	// All Roles
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<UserRoles> getRoles(){
		
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		Criteria criteria = ses.createCriteria(UserRoles.class);
		return (List<UserRoles>)criteria.list();
	}
	
	// All Evidence
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<Evidence> getEvidence(){

		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		Criteria criteria = ses.createCriteria(Evidence.class);
		return (List<Evidence>)criteria.list();
	}
	
	//-------------------------------------------------------------------------------------------------
	// Pagination
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PaginatedResultList<Blog> getBlogs(String search, int start, int max) {
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		try {
			rebuildIndex();
		} catch (InterruptedException e) {
			
			Logging.error(e);
		}
		
		PaginatedResultList<Blog> blogPosts = new PaginatedResultList<>();
		
		FullTextSession fts = Search.getFullTextSession(ses);
		QueryBuilder qb = fts.getSearchFactory().buildQueryBuilder().forEntity(Blog.class).get();
		org.apache.lucene.search.Query searchQuery = 
				qb.bool()
				  .must(
						qb.keyword()
						  .onFields("title", "subtitle", "author.firstName", "author.lastName", "tags.description")
				          .matching(search)
				          .createQuery()
				  ).must(
						qb.keyword()
						  .onField(ACTIVE)
						  .matching("true")
						  .createQuery()
				  )
				.createQuery();
		
		FullTextQuery query = fts.createFullTextQuery(searchQuery, Blog.class);
		query.setFirstResult(start);
		query.setMaxResults(max);
		
		blogPosts.setTotalItems(query.getResultSize());
		
		List<Blog> postList = query.list();
		for (Blog post: postList) {
			Hibernate.initialize(post.getTags());
		}
		blogPosts.setItems(postList);
		
		return blogPosts;
				
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PaginatedResultList<Blog> getBlogs(int start, int max) {
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		PaginatedResultList<Blog> blogPosts = new PaginatedResultList<>();
		
		Criteria criteria = ses.createCriteria(Blog.class).setMaxResults(MAX_POSTS_PER_PAGE*PAGES_TO_LOAD);
		criteria.add(Restrictions.eq(ACTIVE, true));
		criteria.setProjection(Projections.rowCount());
		
		
		Criteria allPostsCount = ses.createCriteria(Blog.class).add(Restrictions.eq(ACTIVE, true));
		blogPosts.setTotalItems((long)allPostsCount.uniqueResult());
		
		
		criteria = ses.createCriteria(Blog.class);
		criteria.add(Restrictions.eq(ACTIVE, true));
		criteria.addOrder(Order.desc(PUBLISH));
		// criteria.setFirstResult(start)
		// criteria.setMaxResults(max) 
		List<Blog> postList = criteria.list();
		for (Blog post: postList) {
			Hibernate.initialize(post.getTags());
		}
		blogPosts.setItems(postList);
		
		return blogPosts;
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PaginatedResultList<Blog> getBlogs(User author, int start, int max) {
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		PaginatedResultList<Blog> blogPosts = new PaginatedResultList<>();
		
		Criteria criteria = ses.createCriteria(Blog.class);
		criteria.add(Restrictions.eq("author", author));
		criteria.add(Restrictions.eq(ACTIVE, true));
		criteria.setProjection(Projections.rowCount());
		blogPosts.setTotalItems((long)criteria.uniqueResult());
		
		criteria = ses.createCriteria(Blog.class).setMaxResults(MAX_POSTS_PER_PAGE*PAGES_TO_LOAD);
		criteria.addOrder(Order.desc(PUBLISH));
		criteria.add(Restrictions.eq("author", author));
		criteria.add(Restrictions.eq(ACTIVE, true));
		// criteria.setFirstResult(start)
		// criteria.setMaxResults(max)

		List<Blog> postList = criteria.list();
		for (Blog post: postList) {
			Hibernate.initialize(post.getTags());
		}
		blogPosts.setItems(postList);
		
		return blogPosts;
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PaginatedResultList<Blog> getBlogs(Tags category, int start, int max) {
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		PaginatedResultList<Blog> blogPosts = new PaginatedResultList<>();
		
		Criteria criteria = ses.createCriteria(Blog.class).setMaxResults(MAX_POSTS_PER_PAGE*PAGES_TO_LOAD);
		criteria.createAlias("tags", "t");
		criteria.add(Restrictions.eq("t.tagId", category.getTagId()));
		criteria.add(Restrictions.eq(ACTIVE, true));
		criteria.setProjection(Projections.rowCount());
		blogPosts.setTotalItems((long)criteria.uniqueResult());
		
		criteria = ses.createCriteria(Blog.class).setMaxResults(MAX_POSTS_PER_PAGE*PAGES_TO_LOAD);
		criteria.addOrder(Order.desc(PUBLISH));
		criteria.createAlias("tags", "t");
		criteria.add(Restrictions.eq("t.tagId", category.getTagId()));
		criteria.add(Restrictions.eq(ACTIVE, true));
		// criteria.setFirstResult(start)
		// criteria.setMaxResults(max)

		List<Blog> postList = criteria.list();
		for (Blog post: postList) {
			Hibernate.initialize(post.getTags());
		}
		blogPosts.setItems(postList);
		
		return blogPosts;
	}
	
	public void rebuildIndex() throws InterruptedException {
		if (!indexBuilt) {
			FullTextSession fullTextSession = Search.getFullTextSession(session);
			fullTextSession.createIndexer().startAndWait();
			indexBuilt = true;
		}
	}

	@Override
	public Blog getBlog(int id) {
		Session ses = sessionFactory.getCurrentSession();
		Criteria criteria = ses.createCriteria(Blog.class).add(Restrictions.eq("blogId", id));
		Blog blog = (Blog) criteria.uniqueResult();
		Hibernate.initialize(blog.getTags());
		return blog;
	}
}
