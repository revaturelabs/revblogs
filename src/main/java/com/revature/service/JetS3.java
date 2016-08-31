package com.revature.service;

public interface JetS3 {
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
