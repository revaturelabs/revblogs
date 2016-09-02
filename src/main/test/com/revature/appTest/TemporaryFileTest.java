package com.revature.appTest;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Test;

import com.revature.app.TemporaryFile;

public class TemporaryFileTest {

	TemporaryFile temporaryFile = null;
	
	@Test
	public void testPlainString() throws IOException {
		String fileName = "myFileName.txt";
		String contents = "SillyFile";
		temporaryFile = TemporaryFile.make(fileName, contents);
		File testTempFile = temporaryFile.getTemporaryFile();
		FileReader fileReader = new FileReader(testTempFile);
		BufferedReader buff = new BufferedReader(fileReader);
		String line = buff.readLine();
		while ( (line = buff.readLine()) != null ) {
			assertTrue(line.equals(contents));
		}
		assertTrue(testTempFile.getName().equals(fileName));
		buff.close();
		fileReader.close();
		temporaryFile.destroy();
	}
	
	@After
	public void cleanup() {
		//boolean wasNull = temporaryFile == null || temporaryFile.isDestroyed();
		temporaryFile.destroy();
	}

}
