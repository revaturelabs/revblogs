package com.revature.service.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {
	public static void sendMail(String email, String password) {
    	Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("danpgdr@gmail.com","danpickles1");
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("danpgdr@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email));
			message.setSubject("(DO NOT REPLY)");
			message.setText("Your Password is: "+password
					+"\n Log in with your email."
					+ "This is currently nonfunctional.");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			//nothing
		}
    }
}
