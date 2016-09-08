package com.revature.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.dto.BlogPostCollectionDTO;
import com.revature.service.BusinessDelegate;
import com.revature.service.Logging;

@Controller
@RequestMapping("/api")
public class AjaxController {
	private Logger log = Logger.getRootLogger();
	private BusinessDelegate businessDelegate;
	
	public BusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}
	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}
	
	@RequestMapping(value="/posts", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<BlogPostCollectionDTO> getPosts (
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="per_page", required=false, defaultValue="10") int perPage,
			@RequestParam(value="author", required=false, defaultValue="0") int authorId,
			@RequestParam(value="category", required=false, defaultValue="0") int tagId,
			@RequestParam(value="q", required=false) String searchQuery,
			HttpServletRequest request) {
			
		BlogPostCollectionDTO responseObject = null;
		
		try {
			log.error(authorId);
			
			User author = businessDelegate.requestUser(authorId);
			log.error(author);
			
			Tags category = businessDelegate.requestTag(tagId);
			
			
			if (author != null) {
				responseObject = businessDelegate.requestBlogPosts(author, page, perPage);
			}
			else if (category != null) {
				responseObject = businessDelegate.requestBlogPosts(category, page, perPage);
			}
			else if (searchQuery != null) {
				responseObject = businessDelegate.searchBlogPosts(searchQuery, page, perPage);
			}
			else {
				responseObject = businessDelegate.requestBlogPosts(page, perPage);
			}
		} catch (IllegalArgumentException e) {
			Logging.info(e);
			return null;
		}
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*"); // TODO: Change this to production S3 domain name
		
		ResponseEntity<BlogPostCollectionDTO> entity = new ResponseEntity<BlogPostCollectionDTO>(responseObject, headers, HttpStatus.OK);
		
		return entity;
	}
	
	@RequestMapping(value="/posts", method=RequestMethod.OPTIONS)
	@ResponseBody
	public ResponseEntity<BlogPostCollectionDTO> getPostsPreflight (HttpServletRequest request, HttpServletResponse response) {
		HttpHeaders headers = new HttpHeaders();
		
        headers.add("Access-Control-Allow-Origin", "*"); // TODO: Change this to production S3 domain name
        headers.add("Access-Control-Allow-Methods", "GET");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
        headers.add("Access-Control-Allow-Max-Age", "1800");
        return new ResponseEntity<BlogPostCollectionDTO>(headers, HttpStatus.OK);
	}
}
