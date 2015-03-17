package eldarvenes;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailTLS {

	private EmailMessage email;
	final String username = "eldarvenes@gmail.com";
	String password;

	public SendMailTLS(EmailMessage email) {
		loadProperties();
		this.email = email;		
	}

	public void sendEmail() {		

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(email.getFrom()));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email.getTo()));
			message.setSubject(email.getSubject());
			message.setText(email.getText());

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void loadProperties() {
		Properties prop = new Properties();
		try {			
			prop.load(Connector.class.getClassLoader()
					.getResourceAsStream("configuration.properties"));			
			password = prop.getProperty("emailPassword");			
		} catch (Exception e) {

		}
	}

}