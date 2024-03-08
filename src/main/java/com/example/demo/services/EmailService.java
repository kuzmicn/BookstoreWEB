package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

@Service
public class EmailService {
	@Autowired
    private JavaMailSender javaMailSender;
	
	public boolean sendEmailWithAttachment(String receiver, String sender, String subject, 
					String text, byte[] jasperReport, String reportName) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom(sender);
			helper.setTo(receiver);
			helper.setSubject(subject);
			helper.setText(text);

			ByteArrayDataSource dataSource = new ByteArrayDataSource(jasperReport, "application/pdf");
			helper.addAttachment(reportName+".pdf", dataSource);
			javaMailSender.send(message);
			
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
}
