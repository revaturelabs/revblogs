package com.revature.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BlogPostCollectionDTO {
	
	int page = 0;
	
	@JsonProperty("total_pages")
	int totalPages = 0;
	
	@JsonProperty("total_posts")
	int totalPosts = 0;
	
	String prev = null;
	String next = null;
	List<BlogPostDTO> posts = new ArrayList<>();
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getTotalPosts() {
		return totalPosts;
	}
	public void setTotalPosts(int totalPosts) {
		this.totalPosts = totalPosts;
	}
	public String getPrev() {
		return prev;
	}
	public void setPrev(String prev) {
		this.prev = prev;
	}
	public String getNext() {
		return next;
	}
	public void setNext(String next) {
		this.next = next;
	}
	public List<BlogPostDTO> getPosts() {
		return posts;
	}
	public void setPosts(List<BlogPostDTO> posts) {
		this.posts = posts;
	}
	public BlogPostCollectionDTO() {
		super();
		
	}
	
	
}
