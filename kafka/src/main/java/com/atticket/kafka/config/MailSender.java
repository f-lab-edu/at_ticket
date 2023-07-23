package com.atticket.kafka.config;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:/env/env.yml"}, factory = YamlLoadFactory.class)
public class MailSender {

	@Value("${mail.smtp_host}")
	private String smtp_host;

	@Value("${mail.smpt_port}")
	private String smtp_port;

	@Value("${mail.user_email}")
	private String user_email;

	@Value("${mail.user_pw}")
	private String user_pw;

	@Value("${mail.receiver_email}")
	private String receiver_email;

	public void Send(String subject, String text) throws Exception {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtp_host);
		props.put("mail.smtp.port", smtp_port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", smtp_host);
		props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user_email, user_pw);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user_email));

			// 받는 이메일
			message.setRecipients(
				Message.RecipientType.TO,
				InternetAddress.parse(receiver_email)
			);

			// 제목
			message.setSubject(subject);

			// 내용
			message.setText(text);

			// 발송
			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
