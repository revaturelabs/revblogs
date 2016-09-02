package com.revature.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.revature.dto.BlogPostCollectionDTO;
import com.revature.service.BusinessDelegate;

@Controller
@RequestMapping("/api")
public class AjaxController {
	
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
			@RequestParam(value="q", required=false) String searchQuery,
			HttpServletRequest request) {
		
		return businessDelegate.requestBlogPosts(page, perPage);
	}
	
}
