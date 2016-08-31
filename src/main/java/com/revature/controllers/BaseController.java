package com.revature.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.revature.beans.Blog;
import com.revature.service.BusinessDelegate;

@Controller
public class BaseController {

	private BusinessDelegate businessDelegate;

	public void setBusinessDelegate(BusinessDelegate businessDelegate){
		this.businessDelegate = businessDelegate;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(HttpServletRequest req, HttpServletResponse resp){
	
		return "login";
	}
	@RequestMapping(value="/create-blog", method=RequestMethod.GET)
	public String createBlog(HttpServletRequest req, HttpServletResponse resp){
		req.setAttribute("blog", new Blog());
		return "create-blog";
	}
	
	@RequestMapping(value="add-blog.do", method=RequestMethod.POST)
	public String addBlog(
			@ModelAttribute("blog") @Valid Blog blog, 
			BindingResult bindingResult,
			HttpServletRequest req,
			HttpServletResponse resp) {
		System.out.println(blog.getBlogTitle());
		System.out.println(blog.getBlogSubtitle());
		System.out.println(blog.getBlogContent());
		return "create-blog";
	}
	
	@RequestMapping(value="/upload-example", method=RequestMethod.GET)
	public ModelAndView uploadExamplePage() {
		ModelAndView mv = new ModelAndView("upload-example");
		return mv;
	}
	
	@RequestMapping(value="/uploadFile.do", method=RequestMethod.POST)
	public void uploadFileHandler(
			//@RequestParam("upc") String upc,
			@RequestParam("file") MultipartFile file
			/*HttpServletRequest req*/,
			HttpServletResponse resp)
	{
		String url = businessDelegate.uploadResource(file.getOriginalFilename(), file);
		System.out.println(url);
		try {
			PrintWriter writer = resp.getWriter();
			writer.append("<html><body><img src=\"" + url + "\" /></body></html>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	*/
}
