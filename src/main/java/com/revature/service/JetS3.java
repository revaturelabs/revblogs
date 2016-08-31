package com.revature.service;

import org.springframework.web.multipart.MultipartFile;

public interface JetS3 {
	/**
	 * Used for uploading html from 
	 */
	public boolean uploadFile(MultipartFile file);
	/**
	 * Used as a base for uploading data to S3
	 */
	public boolean uploadText(String filename,String filedata);
	/**
	 * Used to delete any file from the bucket as long
	 * as the name matches in the S3
	 */
	public boolean delete(String filename);
}
