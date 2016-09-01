package com.revature.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.revature.app.TemporaryFile;
import com.revature.beans.Blog;
import com.revature.beans.User;

public class HtmlWriter {
	
	public static final String DEFAULT_PATH = "src/main/webapp/WEB-INF/pages/";
	private BufferedReader tempReader;
	private BufferedWriter blogWriter;
	private Blog blog;
	private User author;
	private String tempPath;
	private InputStream templateStream;
	
	public HtmlWriter(Blog blog, User author, InputStream templateStream) throws FileNotFoundException {
		this.blog = blog;
		this.author = author;
		this.templateStream = templateStream;
		InputStreamReader isr = new InputStreamReader(templateStream);
		tempReader = new BufferedReader(isr);
	}
	
	public TemporaryFile render() throws IOException {
		String line;
		String title = ""+blog.getBlogTitle().hashCode()+blog.getPublishDate().hashCode();
//		String fileName = outPath+title+".html";
		String fileName = title+".html";
//		tempReader = new BufferedReader(new FileReader(tempPath));
//		byte[] templateStreamBytes = new byte[templateStream.available()];
//		int bytesRead = 0;
//		while ( (bytesRead = templateStream.read(templateStreamBytes)) >= 0 ) {
//			
//		}
		TemporaryFile tempFile = TemporaryFile.make(fileName);
		tempReader = new BufferedReader(new FileReader(tempPath));
		blogWriter = new BufferedWriter(new FileWriter(tempFile.getTemporaryFile()));
		while ((line=tempReader.readLine()) != null) {
			blogWriter.write(line+"\n");
			if (line.contains("post-date"))
				blogWriter.write(blog.getPublishDate().toString()+"\n");
			if (line.contains("post-author"))
				blogWriter.write("by "+author.getFirstName()+" "+author.getLastName()+"\n");
			if (line.contains("post-title"))
				blogWriter.write(blog.getBlogTitle()+"\n");
			if (line.contains("post-body"))
				blogWriter.write(blog.getBlogContent()+"\n");
			if (line.contains("author-name"))
				blogWriter.write(author.getFirstName()+" "+author.getLastName());
			if (line.contains("author-desc"))
				blogWriter.write(author.getDescription());
		}
		blogWriter.close();
		tempReader.close();
		return tempFile;
	}	
}
