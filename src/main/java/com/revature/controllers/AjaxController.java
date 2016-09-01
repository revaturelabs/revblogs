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

@Controller
@RequestMapping("/api")
public class AjaxController {
	
	/* TEMPORARY TEST DATA ACCESS CODE */
	static HashMap<Integer, User> authors = new HashMap<>();
	static HashMap<Integer, Tags> tags = new HashMap<>();
	static HashMap<Integer, Blog> posts = new HashMap<>();
	
	static {
		String[] names = {"Trey McDeane", "Ravi Singh", "Patrick Walsh", "Steven Kelsey", "Jonathan Wilhite", "Justin Forsberg", "Alex Michels", "Carter Smith", "William Wan"};
		for (int i=1; i<=names.length; i++) {
			User u = new User();
			u.setFirstName(names[i-1].split(" ")[0]);
			u.setLastName(names[i-1].split(" ")[1]);
			u.setProfilePicture("http://cdn11.lbstatic.nu/files/users/small/4118542_one-punch-man-saitama.jpg?1461068858");
			u.setUserId(i);
			authors.put(i, u);
		}
		
		String[] tagArr = {"Java", ".NET", "SDET", "JavaScript", "Design", "Productivity", "DevOps", "Fortran", "Appian"};
		for (int i=1; i<=tagArr.length; i++) {
			tags.put(i, new Tags(tagArr[i-1]));
		}
		
		NonsenseGenerator ng = NonsenseGenerator.getInstance();
		
		// Generate random posts
		for (int i=1; i<=55; i++) {
			Random rand = new Random();
			Blog post = new Blog();
			post.setBlogTitle(ng.makeHeadline());
			post.setBlogSubtitle(ng.makeHeadline());

			int numParagraphs = rand.nextInt(10) + 5;
			StringBuilder content = new StringBuilder();
			for (int j=0; j<numParagraphs; j++) {
				content.append("<p>");
				content.append(ng.makeText((rand.nextInt(5))+3));
				content.append("</p>");
			}
			post.setBlogContent(content.toString());
			
			int date2008 = 1199145600;
			int date2017 = 1483228800;
			int randomDate = (rand.nextInt(date2017-date2008))+date2008;
			
			post.setPublishDate(new Date(randomDate));
			Set<Evidence> files = new HashSet<>();
			files.add(new Evidence("http://placekitten.com/500/500"));
			post.setEvidences(files);
			Set<Tags> t = new HashSet<>();
			t.add(new Tags("Java"));
			post.setTags(t);
			
			post.setBlogId(i);
			
			posts.put(i, post);
		}
	}
	/* END TEMPORARY DATA ACCESS CODE */
	
	@RequestMapping(value="/posts")
	@ResponseBody
	public BlogPostCollectionDTO getPosts(
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="per_page", required=false, defaultValue="10") int perPage,
			@RequestParam(value="author", required=false, defaultValue="0") int authorId,
			@RequestParam(value="q", required=false) String searchQuery,
			HttpServletRequest request) {
		
		// Instantiate post collection DTO
		BlogPostCollectionDTO postCollection = new BlogPostCollectionDTO();
		
		final List<BlogPostDTO> postList = postCollection.getPosts();
		posts.values()
			.stream()
			.sorted(new Comparator<Blog>() {
				@Override
				public int compare(Blog p1, Blog p2) {
					return p2.getPublishDate().compareTo(p1.getPublishDate());
				}
			})
			.skip((long)(page-1)*perPage)
			.limit(perPage)
			.forEach(new Consumer<Blog>() {
				@Override
				public void accept(Blog p) {
					postList.add(new BlogPostDTO(p));
				}
			});
		postCollection.setPage(page);
		int totalPosts = posts.values().size();
		int totalPages = (int) Math.ceil((double)totalPosts/perPage);
		postCollection.setTotalPosts(totalPosts);
		postCollection.setTotalPages(totalPages);
		
		if (page == 1) {
			postCollection.setPrev(null);
		}
		else {
			postCollection.setPrev("");
		}
		if (page == totalPages) {
			postCollection.setNext(null);
		}
		
		return postCollection;
	}
	
}
