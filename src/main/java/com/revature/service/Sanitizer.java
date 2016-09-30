package com.revature.service;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

public class Sanitizer {

	/**
	 * Replaces strings with strong XSS potential with safer, visually equivalent strings
	 * @param str The string to be sanitized
	 * @return a sanitized version of the string
	 */
	public static String sanitize(String str) {
		try {
			return samySanitize(str)
					.replace("{{", "{&#8291;{").replace("}}", "}&#8291;}"); // Angular Braces
			
		} catch (ScanException | PolicyException e) {
			// AntiSamy failed for some reason, run basic (weak) anti-XSS routines
			return str.replace("<", "&lt;").replace(">", "&gt;") // HTML Tags
					.replace("\"", "&quot;").replace("\'", "&#39;") // Quotes
					.replace("{{", "{&#8291;{").replace("}}", "}&#8291;}"); // Angular Braces
		}
	}
	
	private static String samySanitize(String str) throws ScanException, PolicyException {
		AntiSamy samyInstance = new AntiSamy();
		CleanResults cleanResults;
		cleanResults = samyInstance.scan(str);
		return cleanResults.getCleanHTML();
	}
}
