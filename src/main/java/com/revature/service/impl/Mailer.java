package com.revature.service.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.revature.service.Logging;

public class Mailer {
	private Mailer(){
		throw new IllegalAccessError("Utility Class");
	}
	public static void sendMail(String newEmail, String newPassword) {
		Logger log = Logger.getRootLogger();
		
		Logging.log(newEmail);
		Logging.log(newPassword);
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
			message.setText("Your Password is: "+newPassword
					+"\n Log in with your email."
					+ "This is currently nonfunctional.");

			Transport.send(message);

			Logging.log("Done");

		} catch (MessagingException e) {
			log.error(e);
		}
    }
}
