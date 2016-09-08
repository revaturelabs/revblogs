package com.revature.data.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
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
public class DAOImpl implements DAO {

	/*
	 * 	Attributes && Getters/Setters
	 * 
	 */
	
	private SessionFactory sessionFactory;
	private Session session;

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

		session.save(obj);
	}
	
	// Batch Add
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void insertRecord(Object[] obj){
		
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);

		for(Object o : obj){
			
			session.save(o);
		}
	}
	
	// Update a Record
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void editRecord(Object obj){
		
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		session.update(obj);
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
			session.delete(obj);
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
		
		Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("email", email));
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
	
	// Property by Type (Enum)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String getProperty(PropertyType type){
		
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		Criteria criteria = session.createCriteria(ApplicationProperties.class);
		ApplicationProperties props = (ApplicationProperties) criteria.uniqueResult();

		String[][] keys = new String[][]{
			
			/*
			 *  These keys are used to decrypt the corresponding properties
			 */
			
			{"CZmTgoznKnJocTkGuFFURvZjUDuVvBhoETorfnzPOfqymleBbOOHfqPCSSty", "pneumonoultramicroscopicsilicovolcanoconiosis"},
			{"GSXWzGGiiDBvlYxTNddabeUOsSPLHoYnibqBEAtRrSDnZPrACvUjBMGxcoBZ", "Pseudopseudohypoparathyroidism"},
			{"cCpQZBETFySMWXeMTQDQomszbDhIgTCWNfjzrBQjwyzcMIrNeFGZggWpzSdQ", "Floccinaucinihilipilification"},
			{"UjVheJqfrHXEuciEaIEibjRYjaxGEJFPrLcZNuugxZQmpHdeoBJRVLFeEDfc", "Antidisestablishmentarianism"},
			{"BhXCFkEevSCHlJMCJyvqhyOiNnKDaoxwcdWrNGxUZySIJspidexHSROVXDAh", "supercalifragilisticexpialidocious"},
			{"RnhHIlwovrapdVzySrOIfmMZPOPOEACAsVScsBIflnsIphgireiIRKkmINdr", "Incomprehensibilities"},
			{"momGKfMimvxYGNKmZCzdXNSBGpvQngTbtvxETwjePoZWyirhkyAWMhkFzxQI", "honorificabilitudinitatibus"},
			{"TuJgzrAAFblqmFUfDvRyNHOtKQjVpxESLwrXecnGMSrSEJyhfkgPGvTccbPJ", "sesquipedalianism"},
			{"JplYSkoJXvxUEIaEZtLMzYugcPINpzArbIoGHjwHwFzdoUtfNfMOetPvvsHn", "METHIONYLTHREONYLTHREONYGLUTAMINYLARGINY"},
			{"iqGJkjoSepUYggqxsZCdxXzCSyjxADhQtsiMPhyNRMxJbGowMrGmlIQETFzC", "Aequeosalinocalcalinoceraceoaluminosocupreovitriolic"},
			{"boosNkoVgLkjnWJUMEeHAGbUmwWhVlBOPZKZjUduUXunxwbsZmnNxKdAWePg", "peobuefdvxjbtoajefspkfuccfngbf"}
		};
		
		String value;
		
		switch(type){
		
			case COMPANY:
				
				value = props.getCompany();
				value = Crypt.decrypt(value, keys[0][0], keys[0][1]);
				return value;
				
			case APP:

				value = props.getApp();
				value = Crypt.decrypt(value, keys[1][0], keys[1][1]);
				return value;
				
			case S3:

				value = props.getS3();
				value = Crypt.decrypt(value, keys[2][0], keys[2][1]);
				return value;
				
			case SERVER:

				value = props.getServer();
				value = Crypt.decrypt(value, keys[3][0], keys[3][1]);
				return value;
				
			case JENKINS:

				value = props.getJenkins();
				value = Crypt.decrypt(value, keys[4][0], keys[4][1]);
				return value;
				
			case SONARQUBE:

				value = props.getSonarqube();
				value = Crypt.decrypt(value, keys[5][0], keys[5][1]);
				return value;
				
			case K:

				value = props.getK();
				value = Crypt.decrypt(value, keys[6][0], keys[6][1]);
				return value;
				
			case V:

				value = props.getV();
				value = Crypt.decrypt(value, keys[7][0], keys[7][1]);
				return value;
				
			case FAPP:

				value = props.getFapp();
				value = Crypt.decrypt(value, keys[8][0], keys[8][1]);
				return value;
				
			case LINKTOKEN:

				value = props.getLinkToken();
				value = Crypt.decrypt(value, keys[9][0], keys[9][1]);
				return value;
				
			case S3BUCKET:

				value = props.getBucketURL();
				value = Crypt.decrypt(value, keys[10][0], keys[10][1]);
				return value;	
				
			default:

				return null;
		}
	}
	
	//Pull a Single Role
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public UserRoles getRoles(String role) {
		
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		Criteria criteria = session.createCriteria(UserRoles.class);
		criteria.add(Restrictions.eq("role", role));
		return (UserRoles) criteria.uniqueResult();
	}
	
	// All Users
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<User> getUsers(){
			
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		Criteria criteria = session.createCriteria(User.class);
		return (List<User>)criteria.list();
	}
	
	//Pull a single Role
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public UserRoles getRoles(int roleId) {
		
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		Criteria criteria = session.createCriteria(UserRoles.class);
		criteria.add(Restrictions.eq("userRoleId", roleId));
		return (UserRoles) criteria.uniqueResult();
	}
	
	// All Blogs
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<Blog> getBlogs(){
		
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		Criteria criteria = session.createCriteria(Blog.class);
		return (List<Blog>)criteria.list();
	}
	
	// All Tags
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<Tags> getTags(){
		
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		Criteria criteria = session.createCriteria(Tags.class);
		return (List<Tags>)criteria.list();
	}
	
	// All Roles
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<UserRoles> getRoles(){
		
		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		Criteria criteria = session.createCriteria(UserRoles.class);
		return (List<UserRoles>)criteria.list();
	}
	
	// All Evidence
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<Evidence> getEvidence(){

		Session ses = sessionFactory.getCurrentSession();
		setSession(ses);
		
		Criteria criteria = session.createCriteria(Evidence.class);
		return (List<Evidence>)criteria.list();
	}
	
	//-------------------------------------------------------------------------------------------------
	// Pagination
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PaginatedResultList<Blog> getBlogs(String search, int start, int max) {
		Session session = sessionFactory.getCurrentSession();
		setSession(session);
		
		// TODO: Remove in production
		/**
		try {
			rebuildIndex();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		PaginatedResultList<Blog> blogPosts = new PaginatedResultList<>();
		
		FullTextSession fts = Search.getFullTextSession(session);
		QueryBuilder qb = fts.getSearchFactory().buildQueryBuilder().forEntity(Blog.class).get();
		org.apache.lucene.search.Query searchQuery = 
				qb.keyword()
				  .onFields("title", "subtitle", "author.firstName", "author.lastName", "tags.description")
				  .matching(search)
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
		Session session = sessionFactory.getCurrentSession();
		setSession(session);
		
		PaginatedResultList<Blog> blogPosts = new PaginatedResultList<>();
		
		Criteria criteria = session.createCriteria(Blog.class);
		criteria.setProjection(Projections.rowCount());
		blogPosts.setTotalItems((long)criteria.uniqueResult());
		
		criteria = session.createCriteria(Blog.class);
		criteria.addOrder(Order.desc("publishDate"));
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
	public PaginatedResultList<Blog> getBlogs(User author, int start, int max) {
		Session session = sessionFactory.getCurrentSession();
		setSession(session);
		
		PaginatedResultList<Blog> blogPosts = new PaginatedResultList<>();
		
		Criteria criteria = session.createCriteria(Blog.class);
		criteria.add(Restrictions.eq("author", author));
		criteria.setProjection(Projections.rowCount());
		blogPosts.setTotalItems((long)criteria.uniqueResult());
		
		criteria = session.createCriteria(Blog.class);
		criteria.addOrder(Order.desc("publishDate"));
		criteria.add(Restrictions.eq("author", author));
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
	public PaginatedResultList<Blog> getBlogs(Tags category, int start, int max) {
		Session session = sessionFactory.getCurrentSession();
		setSession(session);
		
		PaginatedResultList<Blog> blogPosts = new PaginatedResultList<>();
		
		String hql = "from Tags where tagId eq :tagId left join Blog order by publishDate";
		
		Query query = session.createQuery("select count(*) " + hql).setInteger("tagId", category.getTagId());
		blogPosts.setTotalItems((long)query.uniqueResult());
		
		query = session.createQuery(hql).setInteger("tagId", category.getTagId());
		query.setFirstResult(start);
		query.setMaxResults(max);
		
		List<Blog> postList = query.list();
		for (Blog post: postList) {
			Hibernate.initialize(post.getTags());
		}
		blogPosts.setItems(postList);
		
		return blogPosts;
	}
	
	private boolean indexBuilt = false;
	
	public void rebuildIndex() throws InterruptedException {
		if (!indexBuilt) {
			FullTextSession fullTextSession = Search.getFullTextSession(session);
			fullTextSession.createIndexer().startAndWait();
			indexBuilt = true;
		}
	}

	@Override
	public Blog getBlog(int id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Blog.class).add(Restrictions.eq("blogId", id));
		Blog blog = (Blog) criteria.uniqueResult();
		Hibernate.initialize(blog.getTags());
		return blog;
	}
}
