package com.revature.beans;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="REV_BLOG_TAGS")
public class Tags {
	
		@Id
		@Column(name="REV_BLOG_TAG_ID")
	private int tagId;
		@Column(name="REV_BLOG_DESCRIPTION", nullable=false)
	private String description;
		@ManyToMany(mappedBy="tags")
	private Set<Blog> blogs;
	
	/**
	 * 	Constructors
	 */
	public Tags() {
		super();
	}
	public Tags(int tagId, String description, Set<Blog> blogs) {
		super();
		this.tagId = tagId;
		this.description = description;
		this.blogs = blogs;
	}
	
	/**
	 * 	Getters & Setters
	 */
	public int getTagId() {
		return tagId;
	}
	public void setTagId(int tagId) {
		this.tagId = tagId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<Blog> getBlogs() {
		return blogs;
	}
	public void setBlogs(Set<Blog> blogs) {
		this.blogs = blogs;
	}
	
}
