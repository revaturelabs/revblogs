package com.revature.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class TemporaryFile {

	private static Logger log = Logger.getRootLogger(); 
	
	private File tempFile;
	private File temporaryDirectory;
	private String temporaryDirectoryPath;
	
	/**
	 * Makes a temporary file on the application servers instance store
	 * out of the given MultipartFile
	 * @param multipartFile the MultipartFile to be made into a temporary File
	 * @return the temporaryFile if created, null otherwise
	 */
	protected TemporaryFile() {
		
		tempFile = null;
		temporaryDirectory = null;
		temporaryDirectoryPath = null;
	}
	
	public static TemporaryFile make(MultipartFile multipartFile) {
		
		TemporaryFile temporaryFileContainer = null;
		try
		{
			temporaryFileContainer = new TemporaryFile();
			if ( temporaryFileContainer.createTemporaryFile(multipartFile) )
				return temporaryFileContainer;
		}
		catch ( IOException e ) {log.info(e);}
		
		if ( temporaryFileContainer != null )
			temporaryFileContainer.destroy();
		
		return null;
	}
	
	public static TemporaryFile make(File file) {
		
		TemporaryFile temporaryFileContainer = null;
		try
		{
			temporaryFileContainer = new TemporaryFile();
			if ( temporaryFileContainer.createTemporaryFile(file) )
				return temporaryFileContainer;
		}
		catch ( IOException e ) {log.info(e);}
		
		if ( temporaryFileContainer != null )
			temporaryFileContainer.destroy();
		
		return null;
	}
	
	public static TemporaryFile make(String fileName, byte[] fileBytes) {
		
		TemporaryFile temporaryFileContainer = null;
		try
		{
			temporaryFileContainer = new TemporaryFile();
			if ( temporaryFileContainer.createTemporaryFile(fileName, fileBytes) )
				return temporaryFileContainer;
		}
		catch ( IOException e ) {log.info(e);}
		
		if ( temporaryFileContainer != null )
			temporaryFileContainer.destroy();
		
		return null;
	}
	
	public static TemporaryFile make(byte[] fileBytes) {
		return make("temp.tmp", fileBytes);
	}
	
	public static TemporaryFile make(String fileName, String fileContents) {
		return make(fileName, fileContents.getBytes());
	}
	
	public static TemporaryFile make(String fileName) {
		
		TemporaryFile temporaryFileContainer = null;
		try
		{
			temporaryFileContainer = new TemporaryFile();
			if ( temporaryFileContainer.createTemporaryFile(fileName) )
				return temporaryFileContainer;
		}
		catch ( IOException e ) {log.info(e);}
		
		if ( temporaryFileContainer != null )
			temporaryFileContainer.destroy();
		
		return null;
	}
	
	public File getTemporaryFile() {
		if ( tempFile != null && tempFile.exists() )
			return tempFile;
		
		return null;
	}
	
	public void destroy() {
		removeTemporaryFile();
		removeTemporaryDirectory();
	}
	
	public boolean isDestroyed() {
		return (tempFile == null || !tempFile.exists()) &&
			   (temporaryDirectory == null || !temporaryDirectory.exists());
	}
	
	protected boolean createTemporaryFile(MultipartFile multipartFile) throws IOException {
		
		if ( createTemporaryDirectory() ) {
			tempFile = new File(temporaryDirectoryPath + "/" + multipartFile.getOriginalFilename());
			FileOutputStream fos = new FileOutputStream(tempFile);
			fos.write(multipartFile.getBytes());
		    fos.close();
		    return true;
		}
		return false;
	}
	
	protected boolean createTemporaryFile(File file) throws IOException {
		
		if ( createTemporaryDirectory() ) {
			tempFile = new File(temporaryDirectoryPath + "/" + file.getName());
			FileOutputStream fos = new FileOutputStream(tempFile);
			FileInputStream fis = new FileInputStream(file);
			
			byte[] bytes = new byte[32768];
			int lengthRead = 0;
			while ( (lengthRead = fis.read(bytes)) >= 0 ) {
				fos.write(bytes, 0, lengthRead);
			}
			fis.close();
			fos.close();
			return true;
		}
		return false;
	}
	
	protected boolean createTemporaryFile(String fileName, byte[] fileBytes) throws IOException {
		
		if ( createTemporaryDirectory() ) {
			tempFile = new File(temporaryDirectoryPath + "/" + fileName);
			FileOutputStream fos = new FileOutputStream(tempFile);
			fos.write(fileBytes);
			fos.close();
			return true;
		}
		return false;
	}
	
	protected boolean createTemporaryFile(String fileName) throws IOException {
		
		if ( createTemporaryDirectory() ) {
			tempFile = new File(temporaryDirectoryPath + "/" + fileName);
			return true;
		}
		return false;
	}
	
	protected boolean removeTemporaryFile() {
		return tempFile == null ||
			   !tempFile.exists() ||
			   tempFile.delete();
	}
	
	protected boolean createTemporaryDirectory() {
		
		temporaryDirectoryPath = System.getProperty("java.io.tmpdir")
				+ "tmp" + System.nanoTime();
		temporaryDirectory = new File(temporaryDirectoryPath);
		return temporaryDirectory.exists() ||
			   temporaryDirectory.mkdir();
	}
	
	protected boolean removeTemporaryDirectory() {
				
		return temporaryDirectory == null ||
			   !temporaryDirectory.exists() ||
			   temporaryDirectory.delete();
	}
}
