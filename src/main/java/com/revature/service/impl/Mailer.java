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

import org.apache.log4j.Logger;

public class Mailer {
	private Mailer(){
		throw new IllegalAccessError("Utility Class");
	}
	public static void sendMail(String newEmail, String newPassword) {
		Logger log = Logger.getRootLogger();
		
    	Properties props = new Properties();
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.host", "smtp.gmail.com");
    	props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("d.p.pgdr@gmail.com","danpickles1");
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("d.p.pgdr@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(newEmail));
			message.setSubject("(DO NOT REPLY)");
			
			 // This mail has 2 part, the BODY and the embedded image
	         MimeMultipart multipart = new MimeMultipart("related");

	         // first part (the html)
	         BodyPart messageBodyPart = new MimeBodyPart();
	         String htmlText = "<!DOCTYPE html>"
	         				+ "<html>"
	         				+ 	"<body style=\"background-color: #F9F9F9;\">"
	         				+ 			"<div style=\"margin: 5% 16% 5% 16%;\">"
	         				+ 				"<div style=\"background-color: #FFFFFF;"
	         				+ 				"padding: auto 15px auto 15px;"
	         				+ 				"margin: auto auto auto auto;\">"
	         				+ 					"<h1>Hello</h1>"
	         				+ 					"<h2>You have been invited to Revature Blogs</h2>"
	         				+ 					"<p>Here is your password: "+newPassword+"</p>"
	         				+ 					"<img src=\"http://blogs.pjw6193.tech/content/resources/img/rev-brand.png\">"
	         				+ 					"<p>"
	         				+ 						"<span style=\"color:gray;\">11730 Plaza America Dr. | Suite 205 | Reston, VA 20190 | </span>"
	         				+						"<a href=\"https://revature.com/\">http://revature.com</a>"
	         				+ 					"</p>"
	         				+ 				"</div>"
	         				+ 			"</div>"
	         				+ 	"</body>"
	         				+ "</html>";
	         messageBodyPart.setContent(htmlText, "text/html");
	         // add it
	         multipart.addBodyPart(messageBodyPart);

	         // put everything together
	         message.setContent(multipart);
			
			/*message.setText("Your Password is: "+newPassword
					+"\nLog in with your email and proceed to the link below."
					+ "\nhttp://localhost:7001/revblogs/loginPage");*/

			Transport.send(message);

		} catch (MessagingException e) {
			log.error(e);
		}
    }

}