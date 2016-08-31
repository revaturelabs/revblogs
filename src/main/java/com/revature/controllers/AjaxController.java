package com.revature.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.revature.dto.BlogPostCollectionDTO;

@Controller
@RequestMapping("/api")
public class AjaxController {
	
	@RequestMapping(value="/posts")
	@ResponseBody
	public BlogPostCollectionDTO getPosts(
			@RequestParam("page") int page,
			@RequestParam("author") int authorId,
			@RequestParam("q") String searchQuery) {
		return new BlogPostCollectionDTO();
	}
	
}
