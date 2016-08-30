package com.revature.beans;

import java.sql.*;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="REV_BLOG_BLOG")
public class Blog {
	
		@Id
		@Column(name="REV_BLOG_BLOG_ID")
	private int blogId;
		@Column(name="REV_BLOG_BLOG_TITLE", unique=true, nullable=false)
	private String blogTitle;
		@Column(name="REV_BLOG_BLOG_CONTENT", nullable=false)
	private Clob blogContent;
		@Column(name="REV_BLOG_BLOG_VIEWS")
	private int blogViews;
		@Column(name="REV_BLOG_BLOG_PUBLISH_DATE")
	private Timestamp publishDate;
		@Column(name="REV_BLOG_BLOG_ACTIVE", nullable=false)
	private boolean blogActive;
		@OneToMany
		@JoinColumn(name="REV_BLOG_BLOG_EVIDENCE")
	private Set<Evidence> evidences;
		@ManyToMany
		@JoinTable(name="REV_BLOG_BLOG_TAGS",
			joinColumns=@JoinColumn(name="REV_BLOG_BLOG_ID"),
			inverseJoinColumns=@JoinColumn(name="REV_BLOG_TAG_ID"))
	private Set<Tags> tags;
	
	/**
	 *  Constructors
	 */
	public Blog() {
		super();
	}	
	public Blog(int blogId, String blogTitle, Clob blogContent, int blogViews, Timestamp publishDate,
			boolean blogActive, Set<Evidence> evidences, Set<Tags> tags) {
		super();
		this.blogId = blogId;
		this.blogTitle = blogTitle;
		this.blogContent = blogContent;
		this.blogViews = blogViews;
		this.publishDate = publishDate;
		this.blogActive = blogActive;
		this.evidences = evidences;
		this.tags = tags;
	}

	/**
	 *   Getters & Setters
	 */
	public int getBlogId() {
		return blogId;
	}
	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}
	public String getBlogTitle() {
		return blogTitle;
	}
	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}
	public Clob getBlogContent() {
		return blogContent;
	}
	public void setBlogContent(Clob blogContent) {
		this.blogContent = blogContent;
	}
	public int getBlogViews() {
		return blogViews;
	}
	public void setBlogViews(int blogViews) {
		this.blogViews = blogViews;
	}
	public Timestamp getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Timestamp publishDate) {
		this.publishDate = publishDate;
	}
	public boolean isBlogActive() {
		return blogActive;
	}
	public void setBlogActive(boolean blogActive) {
		this.blogActive = blogActive;
	}
	public Set<Evidence> getEvidences() {
		return evidences;
	}
	public void setEvidences(Set<Evidence> evidences) {
		this.evidences = evidences;
	}
	public Set<Tags> getTags() {
		return tags;
	}
	public void setTags(Set<Tags> tags) {
		this.tags = tags;
	}
	
}
