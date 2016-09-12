package com.revature.service.impl;


import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.revature.service.Logging;

public class Mailer {
	private Mailer(){
		throw new IllegalAccessError("Utility Class");
	}
	public static void sendMail(String...emailInfo) {
		
		// Prepare the properties required to perform an SSL connection to the gmail server
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		// Create a session with the properties and credentials to the gmail account linked to the server
		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("revblogs@gmail.com","danpickles1");
				}
			});

		try {
			// Start an email to be sent
			Message message = new MimeMessage(session);
			
			// Part of the email designating where the email is coming from
			message.setFrom(new InternetAddress("revblogs@gmail.com"));
			
			// Part of the email designating where the email is being sent to
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(emailInfo[0]));
			
			// Part of the email that contains the subject
			message.setSubject("Revature Blog Registration (DO NOT REPLY)");
			
			// Create a body that contains multiple parts
	        MimeMultipart multipart = new MimeMultipart("related");

	        // first part (the html)
	        BodyPart messageBodyPart = new MimeBodyPart();
	        String htmlText = "";
	        
	        // Constructs the body of the email in a form of html tables
	        // 3 arguments references a password being reset
	        if(emailInfo.length == 3){
	        	htmlText = "<!DOCTYPE html>"
	         				+ "<html>"
	         				+ 	"<body style=\"background-color: #F9F9F9;\">"
	         				+ 		"<table style=\"background-color: #FFFFFF;\" align=\"center\">"
	         				+ 			"<tr>"
	         				+ 				"<td width=\"10%\"></td>"
	         				+ 				"<td width=\"40%\"></td>"
	         				+ 				"<td width=\"50%\"><img style=\"display: block; margin: auto 0px auto 50%;\" src=\"http://blogs.pjw6193.tech/content/resources/img/rev-brand.png\"></td>"
	         				+ 			"</tr>"
	         				+ 			"<tr>"
	         				+ 				"<td></td>"
	         				+ 				"<td colspan=\"2\">"
	         				+ 					"<h1>Hello"+emailInfo[2]+",</h1>"
	         				+ 					"<h2>Your password has been reset.</h2>"
	         				+ 					"<p>Here is your new password: <strong>"+emailInfo[1]+"</strong></p>"
	         				+					"<p> Please proceed to <a href=\"http://localhost:7001/revblogs/loginPage\">http://blogs.pjw6193.tech</a> and login to change your password.</p>"
	         				+ 					"<br /><br /><br /><br /><br />"
	         				+ 					"<img src=\"http://blogs.pjw6193.tech/content/resources/img/rev-brand.png\">"
	         				+ 					"<p>"
	         				+ 						"<span style=\"color:gray;\">11730 Plaza America Dr. | Suite 205 | Reston, VA 20190 | </span>"
	         				+						"<a href=\"https://revature.com/\">http://revature.com</a>"
	         				+ 					"</p>"
	         				+ 				"</td>"
	         				+			"</tr>"
	         				+ 		"</table>"
	         				+ 	"</body>"
	         				+ "</html>";
	         }
	         // Body of the email for which a new account is made and their password is sent
	         else{
	        	 htmlText = "<!DOCTYPE html>"
	         				+ "<html>"
	         				+ 	"<body style=\"background-color: #F9F9F9;\">"
	         				+ 		"<table style=\"background-color: #FFFFFF;\" align=\"center\">"
	         				+ 			"<tr>"
	         				+ 				"<td width=\"10%\"></td>"
	         				+ 				"<td width=\"40%\"></td>"
	         				+ 				"<td width=\"50%\"><img style=\"display: block; margin: auto 0px auto 50%;\" src=\"http://blogs.pjw6193.tech/content/resources/img/rev-brand.png\"></td>"
	         				+ 			"</tr>"
	         				+ 			"<tr>"
	         				+ 				"<td></td>"
	         				+ 				"<td colspan=\"2\">"
	         				+ 					"<h1>Welcome New User,</h1>"
	         				+ 					"<h2>You have been invited to Revature Blogs</h2>"
	         				+ 					"<p>Your Username will be: <strong>"+emailInfo[0]+"</strong></p>"
	         				+ 					"<p>Here is your password: <strong>"+emailInfo[1]+"</strong></p>"
	         				+ 					"<p> Please proceed to <a href=\"http://localhost:7001/revblogs/loginPage\">http://blogs.pjw6193.tech</a> and login to change your password.</p>"
	         				+ 					"<br /><br /><br /><br /><br />"
	         				+ 					"<img src=\"http://blogs.pjw6193.tech/content/resources/img/rev-brand.png\">"
	         				+ 					"<p>"
	         				+ 						"<span style=\"color:gray;\">11730 Plaza America Dr. | Suite 205 | Reston, VA 20190 | </span>"
	         				+						"<a href=\"https://revature.com/\">http://revature.com</a>"
	         				+ 					"</p>"
	         				+ 				"</td>"
	         				+			"</tr>"
	         				+ 		"</table>"
	         				+ 	"</body>"
	         				+ "</html>";
	         }
	         messageBodyPart.setContent(htmlText, "text/html");
	         // add it
	         multipart.addBodyPart(messageBodyPart);

	         // put everything together
	         message.setContent(multipart);
			
	         // Send the email
			 Transport.send(message);

		} catch (MessagingException e) {
			Logging.error(e);
		}
    }


}