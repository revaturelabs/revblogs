package com.revature.beans;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="PP_TAGS")
public class Tags {
	
	//----------------------------------
	// Attributes
	@Id
	@Column(name="TAG_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="tagSequence")
	@SequenceGenerator(name="tagSequence",sequenceName="TAG_SEQUENCE",initialValue=1,allocationSize=1)
	private int tagId;
	
	@Column(name="TAG_DESCRIPTION", unique=true, nullable=false)
	private String description;
	
	//----------------------------------
	// Realationship Mapping
	@ManyToMany(mappedBy="tags")
	private Set<Blog> blogs;
	
	/**
	 * 	Constructors
	 */
	public Tags() {
		super();
	}
	public Tags(String description) {
		super();
		this.description = description;
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
