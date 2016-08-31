package com.revature.service.impl;

import java.io.IOException;

import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.acl.AccessControlList;
import org.jets3t.service.acl.GroupGrantee;
import org.jets3t.service.acl.Permission;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;
import org.springframework.web.multipart.MultipartFile;

import com.revature.service.JetS3;

public class JetS3Impl implements JetS3{
	private static AWSCredentials credentials;
	private static S3Service s3;
	//This pushes to Patrick's S3
	//private final static String BUCKET = "dan-pickles-jar";
	//private final static String BUCKET = "jjf28-bucket";
	private final static String BUCKET = "alpha-beta-jar";
	static
	{
		//Temporarily changed credentials
		//Get rid of spaces for correct key
		credentials = new AWSCredentials("AKIAIK25JLJZBAYEQDJQ", "Uzdkfp2JZdwoK4xZVMq26i3Ot6IuQKm0ac+i/cs8");
		/*credentials = new AWSCredentials(
				"AKIAJVEAKBOMV6NISKKA", "bYRtQq1LepU6C9UeRpPceddfl0pXvykV");*/
		s3 = new RestS3Service(credentials);
	}
	
	/**
	 * Attempts to upload a resource (such as an image) to the S3 server
	 * @param fileName the destination name of the file, a valid extension should be included
	 * @param file a file that is to be uploaded to the database
	 * @return the URL where the file was uploaded if successful, null otherwise
	 */
	public String uploadResource(String fileName, MultipartFile file) {
		return uploadFile("content/resources/" + System.nanoTime() + "/", fileName, file);
	}
	
	/**
	 * Attempts to upload a front-end page to the S3 server
	 * @param fileName the destination name of the file, a valid extension should be included
	 * @param file a file that is to be uploaded to the database
	 * @return the URL where the file was uploaded if successful, null otherwise
	 */
	public String uploadPage(String fileName, String pageContent) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Attempts to upload a file to the S3 server
	 * @param folderPath the path to the folder this file will be stored at starting at the S3 root
	 * @param fileName the destination name of the file, a valid extension should be included
	 * @param file a File that is to be uploaded to the database
	 * @return the URL where the file was uploaded if successful, null otherwise
	 */
	protected String uploadFile(String folderPath, String fileName, MultipartFile file) {
		
		try {
			S3Bucket bucket = s3.getBucket(BUCKET);
			S3Object s3Obj = new S3Object(folderPath + fileName);
			s3Obj.setContentType(file.getContentType());
			AccessControlList acl = new AccessControlList();
			acl.setOwner(bucket.getOwner());
			acl.grantPermission(GroupGrantee.ALL_USERS, Permission.PERMISSION_READ);
			s3Obj.setDataInputStream(file.getInputStream());
			s3Obj.setContentLength(file.getSize());
			s3Obj.setAcl(acl);
			s3.putObject(bucket, s3Obj);
			
			// TODO: Replace with something less hardcoded
			return 
				"https://s3-us-west-2.amazonaws.com/" +
				BUCKET + "/" +
				folderPath +
				fileName;
			
		} catch (S3ServiceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; // Resource could not be uploaded
	}
	
	public boolean uploadFile(MultipartFile mFile)
	{
		try{
			S3Bucket bucket = s3.getBucket(BUCKET);
			S3Object file = new S3Object("test/"+mFile.getOriginalFilename());
			file.setContentType(mFile.getContentType());
			AccessControlList acl = new AccessControlList();
			acl.setOwner(bucket.getOwner());
			acl.grantPermission(GroupGrantee.AUTHENTICATED_USERS, Permission.PERMISSION_READ);
			file.setDataInputStream(mFile.getInputStream());
			file.setContentLength(mFile.getSize());
			file.setAcl(acl);
			s3.putObject(bucket, file);
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}	
			return true;
	}
	public boolean uploadText(String filename,String filedata)
	{
		try{
		S3Bucket bucket = s3.getBucket(BUCKET);
		S3Object file = new S3Object(filename, filedata);
		AccessControlList acl = new AccessControlList();
		acl.setOwner(bucket.getOwner());
		acl.grantPermission(GroupGrantee.AUTHENTICATED_USERS, Permission.PERMISSION_READ);
		file.setAcl(acl);
		s3.putObject(bucket, file);
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}	
		return true;
	}
	public boolean delete(String filename)
	{
		try{
			S3Bucket bucket = s3.getBucket(BUCKET);
			s3.deleteObject(bucket, filename);
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}	
		return true;
	}
}
