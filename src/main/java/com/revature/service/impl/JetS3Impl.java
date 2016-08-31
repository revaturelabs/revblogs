package com.revature.service.impl;

import org.jets3t.service.S3Service;
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
	//Using my(Alex H's) S3 atm
	private final static String BUCKET = "alpha-beta-jar";
	static
	{
		//Temporarily changed credentials
		//Get rid of spaces for correct key
		credentials = new AWSCredentials("AKIAI K25JLJZ BAYEQDJQ", "Uzdkfp2JZdw oK4xZVMq 26i3Ot6IuQKm0ac+i/cs8");
		s3 = new RestS3Service(credentials);
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
