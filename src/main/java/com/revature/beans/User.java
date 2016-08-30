package com.revature.beans;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="REV_BLOG_USER")
public class User {
	
		@Id
		@Column(name="REV_BLOG_USER_ID")
	private int userId;
		@Column(name="REV_BLOG_USERNAME", unique=true, nullable=false)
	private String username;
		@Column(name="REV_BLOG_PASSWORD", nullable=false)
	private String password;
		@Column(name="REV_BLOG_EMAIL", unique=true, nullable=false)
	private String email;
		@Column(name="REV_BLOG_FIRST_NAME", nullable=false)
	private String firstName;
		@Column(name="REV_BLOG_LAST_NAME", nullable=false)
	private String lastName;
		@Column(name="REV_BLOG_PROFILE_PICTURE", unique=true)
	private String profilePicture;
		@Column(name="REV_BLOG_JOB_TITLE", nullable=false)
	private String jobTitle;
		@Column(name="REV_BLOG_LINKEDIN_ADDRESS", unique=true)
	private String linkedInAddress;
		@Column(name="REV_BLOG_DESCRIPTION", nullable=false)
	private String description;
		@ManyToOne
		@JoinColumn(name="REV_BLOG_USER_ROLE_ID", nullable=false)
	private UserRoles userRole;
		@Column(name="REV_BLOG_ACTIVE", nullable=false)
	private boolean active;
		@OneToMany
		@JoinColumn(name="REV_BLOG_BLOGS")
	private Set<Blog> blogs;
		
	/**
	 * 	Constructors
	 */
	public User() {
		super();
	}
	public User(int userId, String username, String password, String email, String firstName, String lastName,
			String profilePicture, String jobTitle, String linkedInAddress, String description, UserRoles userRole,
			boolean active, Set<Blog> blogs) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.profilePicture = profilePicture;
		this.jobTitle = jobTitle;
		this.linkedInAddress = linkedInAddress;
		this.description = description;
		this.userRole = userRole;
		this.active = active;
		this.blogs = blogs;
	}
	
	/**
	 * 	Getters & Setters
	 */
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getLinkedInAddress() {
		return linkedInAddress;
	}
	public void setLinkedInAddress(String linkedInAddress) {
		this.linkedInAddress = linkedInAddress;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public UserRoles getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRoles userRole) {
		this.userRole = userRole;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Set<Blog> getBlogs() {
		return blogs;
	}
	public void setBlogs(Set<Blog> blogs) {
		this.blogs = blogs;
	}		
	
}
