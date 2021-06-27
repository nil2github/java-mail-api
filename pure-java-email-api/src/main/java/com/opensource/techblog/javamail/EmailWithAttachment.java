package com.opensource.techblog.javamail;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailWithAttachment {

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
			
			/* Mail body as HTML content */
			message.setContent(prepareHtmlContent());
			
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

		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 *  This method will set the mime type content to Message
	 * @return Multipart
	 * @throws MessagingException
	 */
	private static Multipart prepareHtmlContent() throws MessagingException, IOException {
		
		/* Step 1: Create MimeBodyPart and set content and its Mime Type */
		BodyPart mimeBody = new MimeBodyPart();
		mimeBody.setContent("<h1> This is HTML content </h1><br> Hello <b> User </b>", "text/html");
		
		/* Step 2: Create MimeMultipart and  wrap the mimebody to it */
		Multipart multiPart = new MimeMultipart();
		multiPart.addBodyPart(mimeBody);
		
		/* Step 3: Attaching the file as separate mime body part  */ 
		MimeBodyPart attachmentBodyPart = new MimeBodyPart();
		attachmentBodyPart.attachFile(new File("D:/dlms.sql"));
		
		/* Step 4: Add the attachment Body part to multiPart  */
		multiPart.addBodyPart(attachmentBodyPart);
		
		/* Step 3: set the multipart content to Message in caller method*/
		
		return multiPart;
	}

}
