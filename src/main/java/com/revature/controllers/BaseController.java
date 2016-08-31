package com.revature.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.revature.app.TemporaryFile;
import com.revature.service.BusinessDelegate;

@Controller
public class BaseController {

	private BusinessDelegate businessDelegate;

	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(HttpServletRequest req, HttpServletResponse resp){
	
		return "login";
	}
	@RequestMapping(value="/create-blog", method=RequestMethod.GET)
	public String createBlog(HttpServletRequest req, HttpServletResponse resp){
	
		return "create-blog";
	}
	
	@RequestMapping(value="/upload-example", method=RequestMethod.GET)
	public ModelAndView uploadExamplePage() {
		ModelAndView mv = new ModelAndView("upload-example");
		return mv;
	}
	
	@RequestMapping(value="/upload-resource", method=RequestMethod.POST)
	public void uploadResourceHandler(@RequestParam("file") MultipartFile file, HttpServletResponse resp)
	{
		String url = businessDelegate.uploadResource(file.getOriginalFilename(), file);
		try {
			PrintWriter writer = resp.getWriter();
			writer.append("<html><body><img src=\"" + url + "\" /></body></html>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/upload-page", method=RequestMethod.POST)
	public void uploadPageHandler(@RequestParam("file") MultipartFile file, HttpServletResponse resp)
	{
		TemporaryFile tempFile = TemporaryFile.make(file);
		File convertedFile = tempFile.getTemporaryFile();
		String url = businessDelegate.uploadPage(convertedFile);
		try {
			PrintWriter writer = resp.getWriter();
			writer.append("<html><body><a href=\"" + url + "\">" + url + "</a></body></html>");
		} catch (IOException e) {
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
