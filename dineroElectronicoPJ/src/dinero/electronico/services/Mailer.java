package dinero.electronico.services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {

	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
	
	static String guser = "tiko.mailapp@gmail.com";
	static String gpass = "esrzdynhxemvielc";		
 
	public static void generateAndSendEmail(String to, String subject, String emailBody) throws AddressException, MessagingException 
	{
 
		//setup Mail Server Properties..
		mailServerProperties = new Properties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		mailServerProperties.put("mail.smtp.host", "smtp.gmail.com");
		mailServerProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

 
		// 2nd ===> get Mail Session.."
		getMailSession = Session.getDefaultInstance(mailServerProperties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(guser, gpass);
			}
		  });
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		generateMailMessage.setSubject(subject);
		generateMailMessage.setContent(emailBody, "text/html");
		Transport.send(generateMailMessage);
	}

}
