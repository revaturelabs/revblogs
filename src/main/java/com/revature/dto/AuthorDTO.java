package com.revature.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.beans.User;

public class AuthorDTO {
	
	int id;
	String name;
	String link;
	
	@JsonProperty("profile_photo")
	String profilePhoto;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public AuthorDTO() {
		super();
	}
	public String getProfilePhoto() {
		return profilePhoto;
	}
	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}
	public AuthorDTO(User author) {
		this();
		this.id = author.getUserId();
		this.name = author.getFirstName() + " " + author.getLastName();
		this.link = "/api/posts?author=" + id;
	}	
}
