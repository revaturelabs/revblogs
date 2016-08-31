package com.revature.dto;

public class BlogPostCollectionDTO {
	
	int page = 0;
	int totalPages = 0;
	int totalPosts = 0;
	String prev = null;
	String next = null;
	BlogPostDTO[] posts = {};
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
	public BlogPostDTO[] getPosts() {
		return posts;
	}
	public void setPosts(BlogPostDTO[] posts) {
		this.posts = posts;
	}
	public BlogPostCollectionDTO() {
		super();
		
	}
	
	
}
