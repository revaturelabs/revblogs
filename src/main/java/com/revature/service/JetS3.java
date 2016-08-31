package com.revature.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface JetS3 {
	
	/**
	 * Attempts to upload a resource (such as an image) to the S3 server
	 * @param fileName the destination name of the file, a valid extension should be included
	 * @param file a file that is to be uploaded to the database
	 * @return the URL where the file was uploaded if successful, null otherwise
	 */
	public String uploadResource(String fileName, MultipartFile file);
	
	/**
	 * Attempts to upload a front-end page to the S3 server
	 * @param file a file that is to be uploaded to the database, the file should have a valid extension
	 * @return the URL where the file was uploaded if successful, null otherwise
	 */
	public String uploadPage(File file);
	
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
