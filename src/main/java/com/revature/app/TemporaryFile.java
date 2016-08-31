package com.revature.app;

import java.io.File;
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
		catch ( IOException t ) {log.info(t);}
		
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
	
	@Override
	protected void finalize() throws Throwable {
		destroy();
		super.finalize();
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
