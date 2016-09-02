package com.revature.controllers;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.revature.beans.Blog;
import com.revature.beans.Evidence;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.dto.BlogPostCollectionDTO;
import com.revature.dto.BlogPostDTO;
import com.revature.nonsense.NonsenseGenerator;
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
		//return new BlogPostCollectionDTO();
		return businessDelegate.requestBlogPosts(page, perPage);
	}
	
}
