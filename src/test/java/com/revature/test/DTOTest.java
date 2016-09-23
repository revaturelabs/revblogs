package com.revature.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.revature.dto.AuthorDTO;
import com.revature.dto.BlogPostCollectionDTO;
import com.revature.dto.BlogPostDTO;
import com.revature.dto.UserDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AuthorDTO.class, BlogPostCollectionDTO.class, 
		BlogPostDTO.class, UserDTO.class})
public class DTOTest {
	
	@Autowired
	private AuthorDTO authorDto;
	
	@Autowired
	private BlogPostCollectionDTO blogPostCollectionDTO;
	
	@Autowired
	private BlogPostDTO blogPostDto;
	
	@Autowired
	private UserDTO userDto;
	
	@Test
	public void TestDTO(){
		assertNotNull(authorDto);
		assertNotNull(blogPostCollectionDTO);
		assertNotNull(blogPostDto);
		assertNotNull(userDto);
	}
	
}
