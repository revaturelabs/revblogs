package com.revature.appTest;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Test;

import com.revature.app.TemporaryFile;

public class TemporaryFileTest {

	TemporaryFile temporaryFile = null;
	
	@Test
	public void testPlainString() throws IOException {
		String contents = "SillyFile";
		temporaryFile = TemporaryFile.make(contents);
		File testTempFile = temporaryFile.getTemporaryFile();
		BufferedReader buff = new BufferedReader(new FileReader(testTempFile));
		String line = buff.readLine();
		while ( (line = buff.readLine()) != null ) {
			assertTrue(line.equals(contents));
		}
		temporaryFile.destroy();
	}
	
	@After
	public void cleanup() {
		//boolean wasNull = temporaryFile == null || temporaryFile.isDestroyed();
		temporaryFile.destroy();
	}

}
