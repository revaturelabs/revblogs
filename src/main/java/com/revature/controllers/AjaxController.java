package com.revature.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@RequestMapping(value="/posts")
	@ResponseBody
	public BlogPostCollectionDTO getPosts (
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="per_page", required=false, defaultValue="10") int perPage,
			@RequestParam(value="author", required=false, defaultValue="0") int authorId,
			@RequestParam(value="category", required=false, defaultValue="0") int tagId,
			@RequestParam(value="q", required=false) String searchQuery,
			HttpServletRequest request) {
		try {
			log.error(authorId);
			
			User author = businessDelegate.requestUser(authorId);
			log.error(author);
			
			Tags category = businessDelegate.requestTag(tagId);
			
			if (author != null) {
				return businessDelegate.requestBlogPosts(author, page, perPage);
			}
			else if (category != null) {
				return businessDelegate.requestBlogPosts(category, page, perPage);
			}
			else if (searchQuery != null) {
				return businessDelegate.searchBlogPosts(searchQuery, page, perPage);
			}
			else {
				return businessDelegate.requestBlogPosts(page, perPage);
			}
		} catch (IllegalArgumentException e) {
			Logging.info(e);
			return null;
		}
	}
}
