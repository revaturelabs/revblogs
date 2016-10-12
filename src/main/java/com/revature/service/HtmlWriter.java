package com.revature.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

import com.revature.app.TemporaryFile;
import com.revature.beans.Blog;
import com.revature.beans.Tags;
import com.revature.beans.User;

public class HtmlWriter {
	
	public static final String DEFAULT_PATH = "src/main/webapp/WEB-INF/pages/";
	private BufferedReader tempReader;
	private BufferedWriter blogWriter;
	private Blog blog;
	private User author;
//	Check if both are necessary
	private InputStream templateStream;
	
	public HtmlWriter(Blog blog, User author, InputStream templateStream) throws FileNotFoundException {
		this.blog = blog;
		this.author = author;
		this.templateStream = templateStream;
		InputStreamReader isr = new InputStreamReader(templateStream);
		tempReader = new BufferedReader(isr);
	}

	public String sanitize(String str) {
		return Sanitizer.sanitize(str);
	}
	
	public TemporaryFile render() throws IOException {
		String line;
		String title = "";
		StringTokenizer st = new StringTokenizer(blog.getBlogTitle().replaceAll("[^A-Za-z0-9 ]", "").toLowerCase());
		while (st.hasMoreTokens()) {
			title +=st.nextToken();
			if (st.hasMoreTokens())
				title += "-";
		}
		String fileName = sanitize(title)+".html";
		String url = "http://blogs.pjw6193.tech/content/pages/" + fileName;
		TemporaryFile tempFile = TemporaryFile.make(fileName);
		File file = tempFile.getTemporaryFile();
		FileWriter fw = new FileWriter(file);
		blogWriter = new BufferedWriter(fw);
		
		while ((line=tempReader.readLine()) != null) {
			blogWriter.write(line+"\n");
			if (line.contains("<title>"))
				blogWriter.write(sanitize(blog.getBlogTitle())+" | Revature Blogs\n");
			if (line.contains("post-date"))
				blogWriter.write(blog.getPublishDate().toString()+"\n");
			if (line.contains("post-author"))
				blogWriter.write("by "+sanitize(author.getFirstName())+" "+sanitize(author.getLastName())+"\n");
			if (line.contains("post-title"))
				blogWriter.write(sanitize(blog.getBlogTitle())+"\n");
			if (line.contains("post-subtitle"))
				blogWriter.write(sanitize(blog.getBlogSubtitle()));
			if (line.contains("post-body"))
				blogWriter.write(blog.getBlogContent()+"\n");
			if (line.contains("author-name"))
				blogWriter.write(sanitize(author.getFirstName())+" "+sanitize(author.getLastName()));
			if (line.contains("author-desc"))
				blogWriter.write(sanitize(author.getDescription()));

			if (line.contains("comments-facebook"))
				blogWriter.write("<div>"+
				"<div class='fb-comments' data-href='http://blogs.pjw6193.tech/content/pages/"+fileName+"' data-numposts='3'></div>"+
				"</div>");
			if (line.contains("url-link")){
				blogWriter.write("<meta property='og:url' content='http://blogs.pjw6193.tech/content/pages/"+fileName+"' />");
			}
			if (line.contains("url-title")){
				blogWriter.write("<meta property='og:title' content="+sanitize(blog.getBlogTitle())+" />");
			}
			if(line.contains("url-description")){
				blogWriter.write("<meta property='og:description' content="+sanitize(blog.getBlogSubtitle())+" />");
			}
			if(line.contains("author-image"))
				blogWriter.write("<img src=" + sanitize(blog.getAuthor().getProfilePicture()) + " alt=\"Revature Author Profile Picture\" />");
			if(line.contains("post-tags")){
				Set<Tags> tags = blog.getTags();
				for(Tags tag: tags){
					blogWriter.write("<a href='' ng-click='getPageWithTagsFromBlogPost(" + tag.getTagId() + ")'>" + sanitize(tag.getDescription()) + "</a> ");
				}
			}
			if(line.contains("facebook-url")){
				blogWriter.write("<input type='text' hidden id='facebookurl' value='"+fileName+"'/>");
			}
			if(line.contains("linkedin-url")){
				blogWriter.write("<a class='btn btn-social-icon btn-linkedin' target='_blank' href='https://www.linkedin.com/shareArticle?mini=true&url=" + url + "'>"														
				+ "<span class='fa fa-linkedin'></span>"	
				+ "</a>");
			}
			if(line.contains("twitter-url")) {
				String twitter = "<a class='btn btn-social-icon btn-twitter' target='_blank' href='https://twitter.com/share?url='"+url+"' class='twitter-share-button' data-show-count='false'>"
						+ "<span class='fa fa-twitter'></span>"				
					    + "</a>";
				blogWriter.write(twitter);
			}
			if (line.contains("post-references-body")) {
				List<String> references = blog.getReferences();
				Integer i=1;
				for (String reference : references ) {
					String cleanReference = sanitize(reference);
					if ( cleanReference != null && cleanReference.length() > 0 ) {
						blogWriter.write("<div class=\"post-reference-item\">"
								+ "[" + i + "] - <a target=\"_blank\" href=" + cleanReference + ">"
								+ cleanReference + "</a></div>");
						i = i+1;
					}
				}
			}
		}
		blogWriter.close();
		tempReader.close();

		return tempFile;
	}	
}
