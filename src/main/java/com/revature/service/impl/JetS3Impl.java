package com.revature.service.impl;

import org.jets3t.service.S3Service;
import org.jets3t.service.acl.AccessControlList;
import org.jets3t.service.acl.GroupGrantee;
import org.jets3t.service.acl.Permission;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;

public class JetS3Impl {
	private static AWSCredentials credentials;
	private static S3Service s3;
	//This pushes to Patrick's S3
	//private final static String BUCKET = "dan-pickles-jar";
	//Using my(Alex H's) S3 atm
	private final static String BUCKET = "dan-pickles-jar";
	static
	{
//		Credentials for Patrick's S3
//		credentials = new AWSCredentials("AKIAISITZJSMPIICMLWQ", "ALNGBXrKFyFvW3ow1ql29aprCdza3ytdeCnAW8Vn+i/cs8");
		credentials = new AWSCredentials("AKIAIK25JLJZBAYEQDJQ", "Uzdkfp2JZdwoK4xZVMq26i3Ot6IuQKm0ac+i/cs8");
		s3 = new RestS3Service(credentials);
	}
	public boolean uploadText(String filename,String filedata)
	{
		try{
		S3Bucket bucket = s3.getBucket(BUCKET);
		S3Object image = new S3Object(filename, filedata);
		AccessControlList acl = new AccessControlList();
		acl.setOwner(bucket.getOwner());
		acl.grantPermission(GroupGrantee.AUTHENTICATED_USERS, Permission.PERMISSION_READ);
		image.setAcl(acl);
		s3.putObject(bucket, image);
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
