package com.revature.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.revature.app.TemporaryFile;
import com.revature.beans.Blog;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.dto.UserDTO;
import com.revature.service.BusinessDelegate;
import com.revature.service.HtmlWriter;
import com.revature.service.Logging;
import com.revature.service.Population;
import com.revature.service.impl.Crypt;
import com.revature.service.impl.Mailer;

@Controller
public class PostController {

	/*
	 * 	Attributes && Getters/Setters
	 * 
	 */
	private BusinessDelegate businessDelegate;
	private Population population;
	private Logging logging;

	public void setBusinessDelegate(BusinessDelegate businessDelegate){
		this.businessDelegate = businessDelegate;
	}
	public BusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}
	public Population getPopulation() {
		return population;
	}
	public void setPopulation(Population population) {
		this.population = population;
	}
	public Logging getLogging() {
		return logging;
	}
	public void setLogging(Logging logging) {
		this.logging = logging;
	}
	
	/*
	 *  Methods that effect the database
	 *  
	 */
	
	// Populate Database (GET used for simplicity. No params are passed)
	@RequestMapping(value="populate.do", method=RequestMethod.GET)
	public String buildDatabase(){
	
		return null;
	}
	
	// Update a User
	@RequestMapping(value="updateUser.do", method=RequestMethod.POST)
	public ModelAndView updateUser(@ModelAttribute("updateUser") @Valid User updateUser, BindingResult bindingResult,
							 HttpServletRequest req, HttpServletResponse resp){
		
		ModelAndView model = new ModelAndView();
		model.setViewName("/profile");
		if(bindingResult.hasErrors()){
			return model;
		}
		
		User loggedIn = (User) req.getSession().getAttribute("user");
		
		//password needed to be decrypted first
		loggedIn.setPassword(Crypt.decrypt(loggedIn.getPassword(), loggedIn.getEmail(), loggedIn.getFullname()));
		//end decryption
		
		loggedIn.setEmail(updateUser.getEmail());
		loggedIn.setFirstName(updateUser.getFirstName());
		loggedIn.setLastName(updateUser.getLastName());
		loggedIn.setJobTitle(updateUser.getJobTitle());
		loggedIn.setLinkedInURL(updateUser.getLinkedInURL());
		loggedIn.setDescription(updateUser.getDescription());
		
		//re-encrypt password
		loggedIn.setPassword(Crypt.encrypt(loggedIn.getPassword(), loggedIn.getEmail(), loggedIn.getFullname()));
		//end re-encryption
		
		req.getSession().setAttribute("user", loggedIn);
		businessDelegate.updateRecord(loggedIn);
		req.setAttribute("updateUser", new User());
		return model;
	}
	
	// Update a User
	@RequestMapping(value="updateUserProfile.do", method=RequestMethod.POST)
	public ModelAndView updateUserProfile(@ModelAttribute("updateUserProfile") @Valid User updateUserProfile, 
							 BindingResult bindingResult, HttpServletRequest req, HttpServletResponse resp){
		
		ModelAndView model = new ModelAndView();
		model.setViewName("/manageusers");
		if(bindingResult.hasErrors()){
			return model;
		}
		
//		User loggedIn = (User) req.getSession().getAttribute("user");
//		
//		//password needed to be decrypted first
//		loggedIn.setPassword(Crypt.decrypt(loggedIn.getPassword(), loggedIn.getEmail(), loggedIn.getFullname()));
//		//end decryption
//		
//		loggedIn.setEmail(updateUser.getEmail());
//		loggedIn.setFirstName(updateUser.getFirstName());
//		loggedIn.setLastName(updateUser.getLastName());
//		loggedIn.setJobTitle(updateUser.getJobTitle());
//		loggedIn.setLinkedInURL(updateUser.getLinkedInURL());
//		loggedIn.setDescription(updateUser.getDescription());
//		
//		//re-encrypt password
//		loggedIn.setPassword(Crypt.encrypt(loggedIn.getPassword(), loggedIn.getEmail(), loggedIn.getFullname()));
//		//end re-encryption
//		
//		req.getSession().setAttribute("user", loggedIn);
//		businessDelegate.updateRecord(loggedIn);
		req.setAttribute("updateUserProfile", new User());
		return model;
	}
	//Create a new User
	/*
	 * @RequestParam("newUser")
	 */
	@RequestMapping(value="createAccount.do", method=RequestMethod.POST)
	public ModelAndView createAccount(HttpServletRequest req, HttpServletResponse resp){

		ModelAndView model = new ModelAndView();
		
		// User Supplied
		String email = req.getParameter("email");
		String role = req.getParameter("role");
		
		// Check if email exists
		if(businessDelegate.requestUsers(email) == null){
			// Generate a Temporary Password
			String password = Crypt.encrypt("7Pas8WoR", email, role);
			String firstName = " ";
			String lastName = " ";
			//String profilePicture - currently not used
			String jobTitle = " ";
			String linkedInURL = null;
			String description = " ";
			// Role Obj from Database
			UserRoles userRole = businessDelegate.requestRoles(role);
			User newUser = new User(email, Crypt.encrypt(password, email, lastName+", "+firstName), firstName, lastName, jobTitle,
					linkedInURL, description, userRole);
			// Save in Database
			businessDelegate.putRecord(newUser);
			// Send Email to Account
			Mailer.sendMail(email, password);
			
			model.setViewName("/home");
			return model;
		}
		
		model.setViewName("/makeClientAccount");
		
		return model;
	}
	
	/*
	 * @RequestParam("newPassword")
	 */
	@RequestMapping(value="updatePassword.do", method=RequestMethod.POST)
	public ModelAndView updatePassword(@ModelAttribute("updatePassword") @Valid UserDTO passwordDTO, BindingResult bindingResult,
							   HttpServletRequest req, HttpServletResponse resp){
		
		ModelAndView model = new ModelAndView();
		model.setViewName("/password");
		if(bindingResult.hasErrors()){
			
			return model;
		}
		
		String password = passwordDTO.getNewPassword();
		
		User loggedIn = (User) req.getSession().getAttribute("user");
		
		loggedIn.setPassword(Crypt.encrypt(password, loggedIn.getEmail(), loggedIn.getFullname()));
		
		if(loggedIn.isNewUser()){
			loggedIn.setNewUser(false);
		}
		
		req.getSession().setAttribute("user", loggedIn);
		businessDelegate.updateRecord(loggedIn);
		return model;
	}
	
	// Updates a Users Profile Picture
	@RequestMapping(value="uploadProfilePicture", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE,
			produces=MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String uploadProfilePicture(@RequestParam("profilePicture") MultipartFile profilePicture, 
			HttpServletRequest req, HttpServletResponse resp)
	{
		String url = businessDelegate.uploadEvidence(profilePicture.getOriginalFilename(), profilePicture);
		User loggedIn = (User) req.getSession().getAttribute("user");
		loggedIn.setProfilePicture(url);
		businessDelegate.updateRecord(loggedIn);
		return loggedIn.getProfilePicture();
	}
	
	// Uploads a Blog Picture
	@RequestMapping(value="/upload-picture", method=RequestMethod.POST)
	public void uploadPictureHandler(@RequestParam("file") MultipartFile file,
			HttpServletRequest req,
			HttpServletResponse resp)
	{
		String url = businessDelegate.uploadEvidence(file.getOriginalFilename(), file);
		try {
			PrintWriter writer = resp.getWriter();
			writer.append("<html><h3>Copy</h3><body><textarea id=\"picLink\" autofocus " +
					"rows=\"5\" cols=\"35\" readonly>" + url +
					"</textarea></body><script>window.onload=function(){" +
					"document.getElementById(\"picLink\").select();};</script></html>");
		} catch (IOException e) {
			Logging.info(e);
		}
	}
	
	@RequestMapping(value="/upload-resource", method=RequestMethod.POST)
	public void uploadResourceHandler(@RequestParam("file") MultipartFile file, HttpServletResponse resp)
	{
		String url = businessDelegate.uploadResource(file.getOriginalFilename(), file);
		try {
			PrintWriter writer = resp.getWriter();
			writer.append("<html><body><img src=\"" + url + "\" /></body></html>");
		} catch (IOException e) {
			Logging.info(e);
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
			Logging.info(e);
		}
	}
	
	public Map<Integer, String> getReferences(HttpServletRequest req) {
		
		int highestReferenceNum = -1;
		TreeMap<Integer, String> references = new TreeMap<Integer, String>();
		for ( Enumeration<String> params = req.getParameterNames();
				params.hasMoreElements(); )
		{
			String paramName = params.nextElement();
			String paramValue = req.getParameter(paramName);
			if ( paramName.startsWith("reference") && paramValue.length() > 0 ) {
				int numberStart = paramName.lastIndexOf('e')+1;
				String referenceNumStr = paramName.substring(numberStart);
				try {
					int referenceNum = Integer.parseInt(referenceNumStr);
					references.put(referenceNum, paramValue);
					highestReferenceNum = Math.max(highestReferenceNum, referenceNum);
				} catch ( NumberFormatException e ) {
					Logging.info(e);
				}
			}
		}
		
		// Remove trailing empty references
		for ( int i=highestReferenceNum; i>=0; i-- ) {
			String ref = references.get(i);
			if ( ref != null && ref.length() <= 0 ) {
				references.remove(i);
			} else {
				break;
			}
		}
		
		return references;
	}
	
	@RequestMapping(value="add-blog.do", method=RequestMethod.POST)
	public String addBlog(
			
			@ModelAttribute("blog") @Valid Blog blog, 
			BindingResult bindingResult,
			HttpServletRequest req,
			HttpServletResponse resp) {
		
		blog.setReferences(getReferences(req));
		
		/*
		 * Check to see if the current blog's title already exists. 
		 * If exists, redirect to current page, if new, go to preview blog page.
		 */
		if(!(Boolean)req.getSession().getAttribute("editingBlogInDatabase")) {
			List<Blog> myBlogs = businessDelegate.requestBlogs();
			for(Blog curBlog : myBlogs){
				if(curBlog.getBlogTitle().equals(blog.getBlogTitle())){
					return "create-blog";
				};
			}
		}
		
		
		User author = (User) req.getSession().getAttribute("user");
		author.getFirstName();
		blog.setAuthor(author);
		blog.setPublishDate(new Date());
		req.getSession().setAttribute("blog", blog);
		return "preview-blog";
	}
	
	@RequestMapping(value="publish.do", method=RequestMethod.POST)
	public String publishBlog(HttpServletRequest req, HttpServletResponse resp) {
		Blog blog = (Blog) req.getSession().getAttribute("blog");
		HtmlWriter htmlWriter;
		String url = "";
		
		/*
		 * Blog Bean will be generated with proper tags and fields
		 */
		if(blog.getBlogTagsString().isEmpty()){
			blog.setTags(new HashSet<Tags>());
		}
		else{
			String tmp = blog.getBlogTagsString();
			List<String> myList = Arrays.asList(tmp.split(","));
			Set<Tags> tmpTags = new HashSet<>();
			List<Tags> dbTags = businessDelegate.requestTags();
			/*
			 * loop through List of tag descriptions the user types in
			 */
			for(String a : myList){
				boolean check = false;
				String tagDesc = a.toLowerCase().replaceAll("\\s+","");
				/*
				 * loop through database Tags to check with user input tags
				 * if theres a match, put instance of database Tag into User bean, if not, create new Tag bean
				 */
				for(Tags b : dbTags){
					if(b.getDescription().equals(tagDesc)){
						tmpTags.add(b);
						check = true;
					}
				}
				if(!check){
					Tags myTag = new Tags(tagDesc);
					businessDelegate.putRecord(myTag);
					tmpTags.add(myTag);
					
				}
			}
			blog.setTags(tmpTags);
		}
		try {
			InputStream templateStream = this.getClass().getClassLoader().getResourceAsStream("template.html");
			htmlWriter = new HtmlWriter(blog, blog.getAuthor(), templateStream);
			TemporaryFile blogTempFile = htmlWriter.render();
			String fileName = blogTempFile.getTemporaryFile().getName();
			url = "http://blogs.pjw6193.tech/content/pages/" + fileName;
			blog.setLocationURL(url);
			if((Boolean)req.getSession().getAttribute("editingBlogInDatabase")) {
				int id = (int) req.getSession().getAttribute("blogToEditId");
				blog.setBlogId(id);
				businessDelegate.updateRecord(blog);
			} else {
				businessDelegate.putRecord(blog);
			}
			businessDelegate.uploadPage(blogTempFile.getTemporaryFile());
			blogTempFile.destroy();
			req.getSession().setAttribute("blog", null);
			req.getSession().setAttribute("editingBlogInDatabase", false);
			req.getSession().setAttribute("blogToEditId", 0);
		} catch (FileNotFoundException e) { 
			Logging.info(e);
		} catch (IOException e1) {
			Logging.info(e1);
		}
		return "redirect: " + url;
	}
	
	@RequestMapping(value="/deleteFile", method=RequestMethod.GET)
	public ModelAndView delete(Blog blog, HttpServletRequest req, HttpServletResponse resp){
		businessDelegate.delete(blog.getBlogTitle());
		String[] str = businessDelegate.getList();
		req.setAttribute("blog", new Blog());
		req.setAttribute("list", str);
		ModelAndView model = new ModelAndView();
		model.setViewName("/management");
		return model;
	}
	
	@RequestMapping(value="delete.do", method=RequestMethod.POST)
	public String deleteUserBlog(HttpServletRequest req, HttpServletResponse resp) {
		String blogLink = req.getParameter("blog-link");
		String cutBlogLink = blogLink.replace("http://blogs.pjw6193.tech/", "");
		System.out.println(cutBlogLink);
		businessDelegate.delete(cutBlogLink);
		return "user-blogs";
	}
}