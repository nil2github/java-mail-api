package com.opensource.techblog.javamail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailWithPlainTextContent {

	public static void main(String[] args) {
		final Properties prop = new Properties();
		prop.put("mail.smtp.username", "<<Your GMAIL Id>>");
		prop.put("mail.smtp.password", "<<Your Gmail Password>>");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true"); // TLS

		Session mailSession = Session.getInstance(prop, new javax.mail.Authenticator() {
			@Override
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(prop.getProperty("mail.smtp.username"), 
							prop.getProperty("mail.smtp.password"));
			}
		});

		try {

			Message message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress("no-reply@gmail.com"));
			message.setSubject("Sending Mail with pure Java Mail API ");
			
			/* Mail body with plain Text */
			message.setText("Hello User 1," 
					+ "\n\n If you read this, means mail sent with Java Mail API successfully");
			
			InternetAddress[] toEmailAddresses = 
					InternetAddress.parse("user1@gmail.com, user2@gmail.com");
			InternetAddress[] ccEmailAddresses = 
					InternetAddress.parse("user21@gmail.com,user22@gmail.com");
			InternetAddress[] bccEmailAddresses = 
					InternetAddress.parse("user31@gmail.com");
						
			message.setRecipients(Message.RecipientType.TO,toEmailAddresses);
			message.setRecipients(Message.RecipientType.CC,ccEmailAddresses);
			message.setRecipients(Message.RecipientType.BCC,bccEmailAddresses);
			

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
